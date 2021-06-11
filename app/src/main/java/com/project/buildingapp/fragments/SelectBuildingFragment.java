package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.buildingapp.R;

public class SelectBuildingFragment extends Fragment {

    private View view;

    private RecyclerView recyclerViewSelectBuilding;
    private CardView cardViewSelectBuilding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_building, container, false);

        // set

        // find view by id
        recyclerViewSelectBuilding = view.findViewById(R.id.recyclerview_selectbuilding);
        cardViewSelectBuilding = view.findViewById(R.id.cardview_selectbuilding);

        // set / laod


        // listeners
        cardViewSelectBuilding.setOnClickListener(selectListener);

        return view;
    }

    private View.OnClickListener selectListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

}