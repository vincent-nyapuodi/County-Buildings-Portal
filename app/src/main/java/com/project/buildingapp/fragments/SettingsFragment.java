package com.project.buildingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

public class SettingsFragment extends Fragment {

    private View view;

    private TextView tvEdit;
    private ImageView imgEdit;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        // set
        ((BottomNavLocker)getActivity()).BottomNavLocked(false);
        ((ToolBarLocker)getActivity()).ToolBarLocked(false);


        // find view by id
        tvEdit = view.findViewById(R.id.tv_edit);
        imgEdit = view.findViewById(R.id.img_edit);

        // set / load data


        // listeners
        tvEdit.setOnClickListener(editListener);
        imgEdit.setOnClickListener(editListener);

        return view;
    }


    private View.OnClickListener editListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToEdit);
        }
    };
}
