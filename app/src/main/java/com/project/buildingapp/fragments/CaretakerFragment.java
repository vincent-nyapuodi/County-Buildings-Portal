package com.project.buildingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

public class CaretakerFragment extends Fragment {

    private View view;

    private RecyclerView recyclerView;
    private TextView tvAddCaretaker;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_caretaker, container, false);

        // set
        ((BottomNavLocker)getActivity()).BottomNavLocked(false);
        ((ToolBarLocker)getActivity()).ToolBarLocked(false);

        // find view by id
        recyclerView = view.findViewById(R.id.recyclerview_caretaker);
        tvAddCaretaker = view.findViewById(R.id.tv_add_caretaker);

        // set / load data


        // listeners
        tvAddCaretaker.setOnClickListener(addListener);

        return view;
    }

    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToAddCaretakerFromCaretaker);
        }
    };
}