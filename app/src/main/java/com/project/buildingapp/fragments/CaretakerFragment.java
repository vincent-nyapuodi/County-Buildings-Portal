package com.project.buildingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.buildingapp.R;
import com.project.buildingapp.adapters.AccountAdapter;
import com.project.buildingapp.adapters.CareTakerAdapter;
import com.project.buildingapp.models.Building;
import com.project.buildingapp.models.CareTaker;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

public class CaretakerFragment extends Fragment {

    private View view;

    private RecyclerView recyclerView;
    private TextView tvAddCaretaker;

    private String email;

    private FirebaseUser user;
    private CareTakerAdapter adapter;
    private FirebaseRecyclerOptions<CareTaker> options;
    private DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_caretaker, container, false);

        // set
        ((BottomNavLocker)getActivity()).BottomNavLocked(false);
        ((ToolBarLocker)getActivity()).ToolBarLocked(false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("table_caretaker");

        // find view by id
        recyclerView = view.findViewById(R.id.recyclerview_caretaker);
        tvAddCaretaker = view.findViewById(R.id.tv_add_caretaker);

        // set / load data
        loadData();

        // listeners
        tvAddCaretaker.setOnClickListener(addListener);

        return view;
    }

    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToAddCaretakerFromCaretaker);
        }
    };

    private String getEmail(){
        if (user != null) {
            email = user.getEmail();
        }
        return email;
    }

    private void loadData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        options = new FirebaseRecyclerOptions.Builder<CareTaker>()
                .setQuery(reference.orderByChild("owneremail").equalTo(getEmail()), CareTaker.class)
                .build();

        adapter = new CareTakerAdapter(options, getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}