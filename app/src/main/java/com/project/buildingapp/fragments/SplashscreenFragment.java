package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;


public class SplashscreenFragment extends Fragment {

    private View view;

    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_splashscreen, container, false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (currentUser==null){
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            Navigation.findNavController(view).navigate(R.id.navigateToLogin);
                        }
                    }, 1000
            );
        }else {
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            NavHostFragment.findNavController(getParentFragment()).navigate(SplashscreenFragmentDirections.navigateToSelectFromSplash());
                        }
                    }, 1000
            );
        }
    }
}