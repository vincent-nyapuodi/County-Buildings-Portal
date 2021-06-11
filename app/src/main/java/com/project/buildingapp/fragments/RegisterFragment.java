package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.buildingapp.R;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;


public class RegisterFragment extends Fragment {

    private View view;

    private EditText txtName, txtEmail, txtPhone;
    private ShowHidePasswordEditText txtPassword;
    private ProgressBar progressBar;
    private Button btnRegister;
    private TextView tvRegisterToLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);

        // set


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

        return view;
    }

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}