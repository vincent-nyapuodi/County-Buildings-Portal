package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.buildingapp.R;
import com.project.buildingapp.adapters.CareTakerAdapter;
import com.project.buildingapp.adapters.FeedbackAdapter;
import com.project.buildingapp.models.CareTaker;
import com.project.buildingapp.models.Comment;
import com.project.buildingapp.utils.BottomNavLocker;


public class FeedbackFragment extends Fragment {

    private View view;

    private TextView tvCertificate;
    private RecyclerView recyclerView;

    private String buildingcodecertificate, certificate;

    private FeedbackFragmentArgs args;

    private FeedbackAdapter adapter;
    private FirebaseRecyclerOptions<Comment> options;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feedback, container, false);

        // set
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);

        reference = FirebaseDatabase.getInstance().getReference().child("table_comments");

        args = FeedbackFragmentArgs.fromBundle(getArguments());
        buildingcodecertificate = args.getBuildingcodeCertificate();
        certificate = args.getCertificate();

        // find view by id
        tvCertificate = view.findViewById(R.id.tv_feedback_certificate);
        recyclerView = view.findViewById(R.id.recyclerview_feedback);

        // set / load data
        loadData();
        tvCertificate.setText(certificate + " certificate");

        // on click listener

        return view;
    }


    private void loadData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        options = new FirebaseRecyclerOptions.Builder<Comment>()
                .setQuery(reference.orderByChild("buildingcode_certificate").equalTo(buildingcodecertificate), Comment.class)
                .build();

        adapter = new FeedbackAdapter(options, getContext());
        recyclerView.setAdapter(adapter);
    }
}