package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.buildingapp.R;
import com.project.buildingapp.adapters.UserBuildings;
import com.project.buildingapp.models.Building;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

import org.jetbrains.annotations.NotNull;


public class UserHomeFragment extends Fragment {

    private View view;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private FirebaseRecyclerOptions<Building> options;
    private UserBuildings adapter;
    private DatabaseReference reference;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_home, container, false);

        // set
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);
        ((ToolBarLocker) getActivity()).ToolBarLocked(false);

        reference = FirebaseDatabase.getInstance().getReference().child("table_building");

        // find view by id
        recyclerView = view.findViewById(R.id.recyclerview_userview);
        refreshLayout = view.findViewById(R.id.refresh_userhome);

        // set / load data
        loadData();

        // listeners
        refreshLayout.setOnRefreshListener(refreshListener);

        return view;
    }

    private void loadData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        options = new FirebaseRecyclerOptions.Builder<Building>()
                .setQuery(reference, Building.class)
                .build();

        adapter = new UserBuildings(options, getContext());
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        }
    };


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {

        MenuItem logout = menu.findItem(R.id.menu_logout);
        MenuItem search = menu.findItem(R.id.menu_searchbar);

        search.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_logout:
                Navigation.findNavController(view).navigate(R.id.navigateToLoginFromUserHome);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "You have Logged out", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_searchbar:

                break;
        }

        return true;
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