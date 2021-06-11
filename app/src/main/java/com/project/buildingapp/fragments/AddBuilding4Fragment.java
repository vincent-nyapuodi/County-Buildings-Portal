package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.project.buildingapp.R;

public class AddBuilding4Fragment extends Fragment {

    private View view;
    private EditText txtArchitectName, txtSupervisorName, txtContractorName;
    private ProgressBar progressBar;
    private Button btnAddBuilding4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_building4, container, false);

        // set


        // find view by id
        txtArchitectName = view.findViewById(R.id.txt_architectname);
        txtSupervisorName = view.findViewById(R.id.txt_supervisorname);
        txtContractorName = view.findViewById(R.id.txt_contractorname);
        progressBar = view.findViewById(R.id.progressbar_addbuilding);
        btnAddBuilding4 = view.findViewById(R.id.btn_add_building4);

        // set / load


        // listeners
        btnAddBuilding4.setOnClickListener(addBuildingListener);

        return view;
    }


    private View.OnClickListener addBuildingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}