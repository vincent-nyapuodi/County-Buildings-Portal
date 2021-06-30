package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;
import com.project.buildingapp.utils.ValidationsClass;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;


public class AddBuilding3Fragment extends Fragment {

    private View view;
    private EditText txtCaretakerName, txtCaretakerPhone, txtCaretakerEmail;
    private Button btnAddBuilding3;

    private String buildingtype, buildingName, buildingcounty, buildingtown, buildingdescription, buildingurl;
    private String caretakername, caretakeremail;
    private int caretakerphone;

    private AddBuilding3FragmentArgs args;

    private ValidationsClass validate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_building3, container, false);

        // set
        ((ToolBarLocker) getActivity()).ToolBarLocked(false);
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);

        validate = new ValidationsClass();

        args = AddBuilding3FragmentArgs.fromBundle(getArguments());

        buildingName = args.getBuildingName();
        buildingtype = args.getBuildingType();
        buildingcounty = args.getBuildingCounty();
        buildingtown = args.getBuildingTown();
        buildingdescription = args.getBuildingDescription();
        buildingurl = args.getBuildingUrl();

        caretakeremail = args.getCaretakerEmail();
        caretakername = args.getCaretakerName();
        caretakerphone = args.getCaretakerPhone();

        Toast.makeText(getContext(), "Url = " + buildingurl, Toast.LENGTH_SHORT).show();

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
            String name = txtCaretakerName.getText().toString().trim();
            String email = txtCaretakerEmail.getText().toString().trim();
            String phone = txtCaretakerPhone.getText().toString().trim();

            if (name.isEmpty()) {
                txtCaretakerName.setFocusable(true);
                txtCaretakerName.setError("Text field is empty");
            } else {
                if (careTaker(txtCaretakerEmail, txtCaretakerPhone)) {
                    int phoneno = Integer.parseInt(phone);

                    AddBuilding3FragmentDirections.NavigateToAddBuilding3FromAddCaretaker action = AddBuilding3FragmentDirections.navigateToAddBuilding3FromAddCaretaker(buildingtype,
                            buildingName, buildingcounty, buildingtown, buildingdescription, buildingurl);
                    action.setCaretakerEmail(email);
                    action.setCaretakerName(name);
                    action.setCaretakerPhone(phoneno);
                    Navigation.findNavController(view).navigate(action);
                } else {
                    Toast.makeText(getContext(), "There is an error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private boolean careTaker(EditText txtMail, EditText txtNo) {

        boolean valid = false;

        String email = txtMail.getText().toString().trim();
        String phone = txtNo.getText().toString().trim();

        validate.setEmail(email);
        validate.setPhoneNo(phone);

        txtCaretakerName.setError(null);
        switch (validate.validateEmail()) {
            case 0:
                txtMail.setError("Text field is empty");
                txtMail.setFocusable(true);
                valid = false;
                break;
            case 1:
                txtMail.setError("Invalid Email");
                txtMail.setFocusable(true);
                valid = false;
                break;
            case 2:
                switch (validate.validatePhoneNo()) {
                    case 0:
                        txtMail.setError(null);
                        txtNo.setError("Text Field is empty");
                        txtNo.setFocusable(true);
                        valid = false;
                        break;
                    case 1:
                        txtMail.setError(null);
                        txtNo.setError("Length should be 10");
                        txtNo.setFocusable(true);
                        valid = false;
                        break;
                    case 2:
                        txtMail.setError(null);
                        txtNo.setError("Should contain numbers only");
                        txtNo.setFocusable(true);
                        valid = false;
                        break;
                    case 3:
                        valid = true;
                        txtMail.setError(null);
                        txtNo.setError(null);
                        break;
                }
                break;
        }

        return valid;
    }
}