package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.buildingapp.R;
import com.project.buildingapp.models.Owner;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;
import com.project.buildingapp.utils.ValidationsClass;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import java.util.UUID;


public class RegisterFragment extends Fragment {

    private View view;

    private EditText txtName, txtEmail, txtPhone;
    private ShowHidePasswordEditText txtPassword;
    private ProgressBar progressBar;
    private Button btnRegister;
    private TextView tvRegisterToLogin;

    private ValidationsClass validate;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);

        // set
        ((ToolBarLocker)getActivity()).ToolBarLocked(false);
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);

        validate = new ValidationsClass();
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("table_owner");


        // find view by id
        txtName = view.findViewById(R.id.txt_register_name);
        txtEmail = view.findViewById(R.id.txt_register_email);
        txtPhone = view.findViewById(R.id.txt_register_phone);
        txtPassword = view.findViewById(R.id.txt_register_password);
        progressBar = view.findViewById(R.id.progressbar_register);
        tvRegisterToLogin = view.findViewById(R.id.tv_registerto_login);
        btnRegister = view.findViewById(R.id.btn_register);

        // set/ load data


        // listeners
        btnRegister.setOnClickListener(registerListener);
        tvRegisterToLogin.setOnClickListener(loginListener);

        return view;
    }

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = txtName.getText().toString().trim();

            if (name.isEmpty()) {
                txtName.setError("Text Field is empty");
                txtName.setFocusable(true);
            } else {
                if (create(txtEmail, txtPhone, txtPassword)) {
                    final String email = txtEmail.getText().toString().trim();
                    int phone = Integer.parseInt(txtPhone.getText().toString().trim());
                    final String password = txtPassword.getText().toString().trim();
                    String uuid = UUID.randomUUID().toString();
                    int customerstatus = 0;

                    Owner owner = new Owner(email, name, email + "_1", phone);

                    reference.push().setValue(owner).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Failed to save data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
    };

    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigateUp();
        }
    };


    private boolean create(EditText txtMail, EditText txtNo, ShowHidePasswordEditText txtPass){

        boolean valid = false;

        String email = txtMail.getText().toString().trim();
        String phone = txtNo.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        validate.setEmail(email);
        validate.setPhoneNo(phone);
        validate.setPassword(password);

        switch (validate.validateEmail()){
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
                        switch (validate.validatePassword()){
                            case 0:
                                txtMail.setError(null);
                                txtPass.setError(null);
                                txtPass.setError("Text field is empty");
                                txtPass.setFocusable(true);
                                valid = false;
                                break;
                            case 1:
                                txtMail.setError(null);
                                txtPass.setError(null);
                                txtPass.setError("Password should be 6 or more characters");
                                txtPass.setFocusable(true);
                                Toast.makeText(getContext(), "Feedback = " + validate.validatePassword(), Toast.LENGTH_LONG).show();
                                valid = false;
                                break;
                            case 2:
                                txtMail.setError(null);
                                txtPass.setError(null);
                                txtPass.setError("At least one uppercase letter, one number");
                                txtPass.setFocusable(true);
                                valid = false;
                                break;
                            case 3:
                                valid = true;
                                txtMail.setError(null);
                                txtPass.setError(null);
                                break;
                        }
                        break;
                }
                break;
        }

        return valid;
    }


    private void register(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(getContext(), "Sucessful Registration", Toast.LENGTH_SHORT).show();

                            Navigation.findNavController(view).navigate(R.id.navigateToSelectFromRegistration);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(getContext(), "Weak Password", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser account) {

        if (account != null) {
            Toast.makeText(getContext(), "U Signed In successfully", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getContext(), "U Didnt signed in", Toast.LENGTH_LONG).show();
        }

    }

}