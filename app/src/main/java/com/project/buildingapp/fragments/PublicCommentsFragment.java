package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.buildingapp.R;
import com.project.buildingapp.adapters.UsersViewCommentAdapter;
import com.project.buildingapp.models.PublicComments;
import com.project.buildingapp.utils.BottomNavLocker;


public class PublicCommentsFragment extends Fragment {

    private View view;

    private RecyclerView recyclerView;

    private String buildingcode;

    private PublicCommentsFragmentArgs args;

    private FirebaseRecyclerOptions<PublicComments> options;
    private UsersViewCommentAdapter adapter;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_public_comments, container, false);

        // set
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);

        reference = FirebaseDatabase.getInstance().getReference().child("table_public_comments");

        // find view by id
        recyclerView = view.findViewById(R.id.recyclerview_publiccomments);

        args = PublicCommentsFragmentArgs.fromBundle(getArguments());
        buildingcode = args.getBuildingCode();


        // set / load data
        loadData();

        // listeners


        return view;
    }

    private void loadData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        options = new FirebaseRecyclerOptions.Builder<PublicComments>()
                .setQuery(reference.orderByChild("buildingcode").equalTo(buildingcode), PublicComments.class)
                .build();

        adapter = new UsersViewCommentAdapter(options, getContext());
        recyclerView.setAdapter(adapter);
        adapter.startListening();
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