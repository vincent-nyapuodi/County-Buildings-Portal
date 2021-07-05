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
import com.project.buildingapp.utils.ValidationsClass;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;


public class UserLoginFragment extends Fragment {

    private View view;

    private EditText txtEmail;
    private ShowHidePasswordEditText txtPassword;
    private TextView tvLoginToRegister;
    private ProgressBar progressBar;
    private Button btnLogin;

    private ValidationsClass validate;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_login, container, false);

        // set
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);

        validate = new ValidationsClass();

        mAuth = FirebaseAuth.getInstance();


        // find view by id
        txtEmail = view.findViewById(R.id.txt_userlogin_email);
        txtPassword = view.findViewById(R.id.txt_userlogin_password);
        tvLoginToRegister = view.findViewById(R.id.tv_userloginto_register);
        progressBar = view.findViewById(R.id.progressbar_userlogin);
        btnLogin = view.findViewById(R.id.btn_userlogin);


        // set / load data


        // listeners
        btnLogin.setOnClickListener(loginListener);
        tvLoginToRegister.setOnClickListener(registerListener);


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

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

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