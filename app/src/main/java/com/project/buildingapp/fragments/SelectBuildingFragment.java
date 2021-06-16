package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

public class SelectBuildingFragment extends Fragment {

    private View view;

    private RecyclerView recyclerViewSelectBuilding;
    private TextView tvAddBuilding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_building, container, false);

        // set
        ((ToolBarLocker)getActivity()).ToolBarLocked(true);
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);

        // find view by id
        recyclerViewSelectBuilding = view.findViewById(R.id.recyclerview_selectbuilding);
        tvAddBuilding = view.findViewById(R.id.tv_addbuilding);

        // set / laod


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

}