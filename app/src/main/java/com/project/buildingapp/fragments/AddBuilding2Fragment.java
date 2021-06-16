package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

public class AddBuilding2Fragment extends Fragment {

    private View view;

    private EditText txtBuildingName, txtTown, txtDescription;
    private Spinner spinnerCounty;
    private CheckBox chkCaretaker;
    private Button btnBuildingPhoto, btnAddBuilding2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_building2, container, false);

        // set
        ((ToolBarLocker)getActivity()).ToolBarLocked(false);
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);

        // find view by id
        txtBuildingName = view.findViewById(R.id.txt_buildingname);
        txtTown = view.findViewById(R.id.txt_location_town);
        txtDescription = view.findViewById(R.id.txt_description);
        spinnerCounty = view.findViewById(R.id.spinner_county);
        chkCaretaker = view.findViewById(R.id.chk_caretaker);
        btnBuildingPhoto = view.findViewById(R.id.btn_buildingphoto);
        btnAddBuilding2 = view.findViewById(R.id.btn_add_building2);

        // set / load data

        // listeners
        btnBuildingPhoto.setOnClickListener(photoListener);
        btnAddBuilding2.setOnClickListener(addBuildingListener);

        return view;
    }

    private View.OnClickListener photoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener addBuildingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (chkCaretaker.isChecked()) {
                Navigation.findNavController(view).navigate(R.id.navigateToAddCaretaker);
            } else {
                Navigation.findNavController(view).navigate(R.id.navigateToAddBuilding3);
            }
        }
    };
}