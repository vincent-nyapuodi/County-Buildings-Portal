package com.project.buildingapp.fragments;

import android.app.UiAutomation;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;


public class EditFragment extends Fragment {

    private View view;

    private EditText txtEditBuildingName, txtEditDescription;
    private Button btnEditBuildingPhoto, btnEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit, container, false);

        // set
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);


        // find view by id
        txtEditBuildingName = view.findViewById(R.id.txt_edit_buildingname);
        txtEditDescription = view.findViewById(R.id.txt_edit_description);
        btnEditBuildingPhoto = view.findViewById(R.id.btn_edit_buildingphoto);
        btnEdit = view.findViewById(R.id.btn_edit);

        // set / load data


        // listeners
        btnEditBuildingPhoto.setOnClickListener(editPhotoListener);
        btnEdit.setOnClickListener(editListener);


        return view;
    }


    private View.OnClickListener editPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener editListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}