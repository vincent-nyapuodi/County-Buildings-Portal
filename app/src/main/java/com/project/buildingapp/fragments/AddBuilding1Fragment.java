package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.buildingapp.R;

public class AddBuilding1Fragment extends Fragment {

    private View view;
    private Button btnPrivate, btnCommercial, btnAddBuilding1;
    private RadioGroup rdgPrivate, rdgCommercial;
    private LinearLayout lytPrivate, lytCommercial;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_building1, container, false);

        // set


        // find view by id
        btnPrivate = view.findViewById(R.id.btn_add_private);
        btnCommercial = view.findViewById(R.id.btn_add_commercial);

        rdgPrivate = view.findViewById(R.id.rdg_private);
        rdgCommercial = view.findViewById(R.id.rdg_commercial);
        lytPrivate = view.findViewById(R.id.lyt_private);
        lytCommercial = view.findViewById(R.id.lyt_commercial);

        btnAddBuilding1 = view.findViewById(R.id.btn_add_building1);

        // set / load data


        // listener
        btnPrivate.setOnClickListener(privateListener);
        btnCommercial.setOnClickListener(commercialListener);

        btnAddBuilding1.setOnClickListener(addBuildingListener);

        return view;
    }


    private View.OnClickListener privateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            TODO : make lytPrivate visible and lytCommercial gone and button enabled true and textcolor white
        }
    };


    private View.OnClickListener commercialListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//          Todo : make lytComercial visible and lytPrivate gone and button enabled true and textcolor white
        }
    };

    private View.OnClickListener addBuildingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}