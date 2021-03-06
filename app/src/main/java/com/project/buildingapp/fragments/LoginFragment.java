package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;
import com.project.buildingapp.utils.ValidationsClass;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class LoginFragment extends Fragment {

    private View view;

    private EditText txtEmail;
    private ShowHidePasswordEditText txtPassword;
    private ProgressBar progressBar;
    private Button btnLogin;
    private TextView tvLogintoregister, tvUser;

    private ValidationsClass validate;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        // set
        ((ToolBarLocker)getActivity()).ToolBarLocked(true);
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);

        mAuth = FirebaseAuth.getInstance();

        validate = new ValidationsClass();

        // find view by id
        txtEmail = view.findViewById(R.id.txt_login_email);
        txtPassword = view.findViewById(R.id.txt_login_password);
        tvLogintoregister = view.findViewById(R.id.tv_loginto_register);
        progressBar = view.findViewById(R.id.progressbar_login);
        btnLogin = view.findViewById(R.id.btn_login);
        tvUser = view.findViewById(R.id.tv_user);

        // set/ load data


        // listeners
        btnLogin.setOnClickListener(loginListener);
        tvLogintoregister.setOnClickListener(registerListener);
        tvUser.setOnClickListener(userListener);

        return view;
    }


    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (login(txtEmail, txtPassword)){
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                loginUser(email, password);
            }
        }
    };

    private View.OnClickListener userListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToUserLogin);
        }
    };

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToRegistration);
        }
    };

    private boolean login(EditText txtMail, ShowHidePasswordEditText txtPass) {
        boolean valid = false;

        String email = txtMail.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        validate.setEmail(email);
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
                switch (validate.validatePassword()) {
                    case 0:
                        txtMail.setError(null);
                        txtPass.setError("Text field is empty");
                        txtPass.setFocusable(true);
                        valid = false;
                        break;
                    case 1:
                        txtMail.setError(null);
                        txtPass.setError("Password should be 6 or more characters");
                        txtPass.setFocusable(true);
                        valid = false;
                        break;
                    case 2:
                        txtMail.setError(null);
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
        return valid;
    }

    private void loginUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(getContext(), "Sucessful login", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.navigateToSelectFromLogin);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(), "Invalid User Credentials.", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(getContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser account) {

        if (account != null) {
            Toast.makeText(getContext(), "U Signed In successfully", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getContext(), "U Didnt signed in", Toast.LENGTH_LONG).show();
        }

    }
}