package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;


public class AddBuilding3Fragment extends Fragment {

    private View view;
    private EditText txtCaretakerName, txtCaretakerPhone, txtCaretakerEmail;
    private Button btnAddBuilding3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_building3, container, false);

        // set
        ((ToolBarLocker)getActivity()).ToolBarLocked(false);
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);

        // find view by id
        txtCaretakerName = view.findViewById(R.id.txt_caretakername);
        txtCaretakerPhone = view.findViewById(R.id.txt_caretakerphone);
        txtCaretakerEmail = view.findViewById(R.id.txt_caretakeremail);
        btnAddBuilding3 = view.findViewById(R.id.btn_add_building3);

        // set / load data


        // listeners
        btnAddBuilding3.setOnClickListener(addBuildingListener);

        return view;
    }


    private View.OnClickListener addBuildingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToAddBuilding3FromAddCaretaker);
        }
    };
}