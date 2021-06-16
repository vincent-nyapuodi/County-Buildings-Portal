package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class LoginFragment extends Fragment {

    private View view;

    private EditText txtname;
    private ShowHidePasswordEditText txtPassword;
    private ProgressBar progressBar;
    private Button btnLogin;
    private TextView tvLogintoregister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        // set
        ((ToolBarLocker)getActivity()).ToolBarLocked(true);
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);

        // find view by id
        txtname = view.findViewById(R.id.txt_login_email);
        txtPassword = view.findViewById(R.id.txt_login_password);
        tvLogintoregister = view.findViewById(R.id.tv_loginto_register);
        progressBar = view.findViewById(R.id.progressbar_login);
        btnLogin = view.findViewById(R.id.btn_login);

        // set/ load data


        // listeners
        btnLogin.setOnClickListener(loginListener);
        tvLogintoregister.setOnClickListener(registerListener);

        return view;
    }


    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToSelectFromLogin);
        }
    };

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToRegistration);
        }
    };
}