package com.project.buildingapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class AddBuilding2Fragment extends Fragment {

    private View view;

    private EditText txtBuildingName, txtTown, txtDescription;
    private Spinner spinnerCounty;
    private CheckBox chkCaretaker;
    private Button btnBuildingPhoto, btnAddBuilding2;

    private AddBuilding2FragmentArgs args;
    private String buildingtype;
    private int SELECT_PICTURE = 200;
    private Uri selectedimageurl;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_building2, container, false);

        // set
        ((ToolBarLocker) getActivity()).ToolBarLocked(false);
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);

        args = AddBuilding2FragmentArgs.fromBundle(getArguments());
        buildingtype = args.getBuildingType();

        // find view by id
        txtBuildingName = view.findViewById(R.id.txt_buildingname);
        txtTown = view.findViewById(R.id.txt_location_town);
        txtDescription = view.findViewById(R.id.txt_description);
        spinnerCounty = view.findViewById(R.id.spinner_county);
        chkCaretaker = view.findViewById(R.id.chk_caretaker);
        btnBuildingPhoto = view.findViewById(R.id.btn_buildingphoto);
        btnAddBuilding2 = view.findViewById(R.id.btn_add_building2);

        // set / load data

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.counties, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCounty.setAdapter(adapter);

        // listeners
        btnBuildingPhoto.setOnClickListener(photoListener);
        btnAddBuilding2.setOnClickListener(addBuildingListener);

        return view;
    }

    private View.OnClickListener photoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            imageChooser();
        }
    };

    private View.OnClickListener addBuildingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String buildingname = txtBuildingName.getText().toString().trim();
            String county = spinnerCounty.getSelectedItem().toString();
            String town = txtTown.getText().toString().trim();
            String description = txtDescription.getText().toString().trim();
            String caretakername = "null";
            int caretakerphone = 0;
            String caretakeremail = "null";

            if (buildingname.isEmpty()) {
                Toast.makeText(getContext(), "Building Name is empty", Toast.LENGTH_SHORT).show();
            } else if (county.equals("-- SELECT COUNTY --")) {
                Toast.makeText(getContext(), "Select County", Toast.LENGTH_SHORT).show();
            } else if (town.isEmpty()) {
                Toast.makeText(getContext(), "Town is empty", Toast.LENGTH_SHORT).show();
            } else if (description.isEmpty()) {
                Toast.makeText(getContext(), "More description is empty", Toast.LENGTH_SHORT).show();
            } else {
                if (chkCaretaker.isChecked()) {
                    AddBuilding2FragmentDirections.NavigateToAddCaretaker action = AddBuilding2FragmentDirections.navigateToAddCaretaker(buildingtype,
                            buildingname, county, town, description, selectedimageurl.toString());
                    action.setCaretakerEmail(caretakeremail);
                    action.setCaretakerName(caretakername);
                    action.setCaretakerPhone(caretakerphone);
                    Navigation.findNavController(view).navigate(action);
                } else {
                    AddBuilding2FragmentDirections.NavigateToAddBuilding3 action = AddBuilding2FragmentDirections.navigateToAddBuilding3(buildingtype,
                            buildingname, county, town, description, selectedimageurl.toString());
                    action.setCaretakerEmail(caretakeremail);
                    action.setCaretakerName(caretakername);
                    action.setCaretakerPhone(caretakerphone);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        }

    };

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                this.selectedimageurl = selectedImageUri;
                Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_LONG).show();
            }
        }
    }
}