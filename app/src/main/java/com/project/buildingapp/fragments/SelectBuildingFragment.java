package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.buildingapp.R;
import com.project.buildingapp.adapters.AccountAdapter;
import com.project.buildingapp.models.Building;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

public class SelectBuildingFragment extends Fragment {

    private View view;

    private RecyclerView recyclerView;
    private TextView tvAddBuilding;

    private String email;

    private FirebaseUser user;
    private AccountAdapter accountAdapter;
    private FirebaseRecyclerOptions<Building> options;
    private DatabaseReference buildingreference, sessionreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_building, container, false);

        // set
        ((ToolBarLocker) getActivity()).ToolBarLocked(true);
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);

        user = FirebaseAuth.getInstance().getCurrentUser();
        sessionreference = FirebaseDatabase.getInstance().getReference().child("table_session");
        buildingreference = FirebaseDatabase.getInstance().getReference().child("table_building");

        // find view by id
        recyclerView = view.findViewById(R.id.recyclerview_selectbuilding);
        tvAddBuilding = view.findViewById(R.id.tv_addbuilding);

        // set / laod
        updateSession();
        loadData();

        // listeners
        tvAddBuilding.setOnClickListener(addBuildingListener);

        return view;
    }

    private View.OnClickListener addBuildingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToAddBuilding1);
        }
    };

    private String getEmail(){
        if (user != null) {
            email = user.getEmail();
        }
        return email;
    }

    private void updateSession() {
        sessionreference.orderByChild("email").equalTo(getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    snap.child("status").getRef().setValue(false);
                    snap.child("email_status").getRef().setValue(getEmail() + "_false");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        options = new FirebaseRecyclerOptions.Builder<Building>()
                .setQuery(buildingreference.orderByChild("owneremail").equalTo(getEmail()), Building.class)
                .build();

        accountAdapter = new AccountAdapter(options, getContext());
        recyclerView.setAdapter(accountAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        accountAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (accountAdapter != null) {
            accountAdapter.stopListening();
        }
    }
}