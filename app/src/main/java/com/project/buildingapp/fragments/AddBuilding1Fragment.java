package com.project.buildingapp.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

public class AddBuilding1Fragment extends Fragment {

    private View view;
    private Button btnAddBuilding1;
    private RadioGroup rdgPrivate, rdgCommercial;
    private LinearLayout lytPrivate, lytCommercial, lytAddBuildingData;
    private CollapsingToolbarLayout collapsingToolbarLayoutAddBuilding1;

    private Button[] btn = new Button[2];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.btn_add_private, R.id.btn_add_commercial};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_building1, container, false);

        // set
        //((ToolBarLocker) getActivity()).ToolBarLocked(false);
        //((BottomNavLocker) getActivity()).BottomNavLocked(true);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Building");

        // find view by id
        rdgPrivate = view.findViewById(R.id.rdg_private);
        rdgCommercial = view.findViewById(R.id.rdg_commercial);
        lytPrivate = view.findViewById(R.id.lyt_private);
        lytCommercial = view.findViewById(R.id.lyt_commercial);
        lytAddBuildingData = view.findViewById(R.id.lyt_addbuilding_data);
        collapsingToolbarLayoutAddBuilding1 = view.findViewById(R.id.collapsingToolBarLayout_addbuilding1);

        btnAddBuilding1 = view.findViewById(R.id.btn_add_building1);

        // set / load data

        // listener
        btnToggle(btnSelectListener);

        btnAddBuilding1.setOnClickListener(addBuildingListener);

        return view;
    }

    private View.OnClickListener addBuildingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String building = "";

            if (rdgPrivate.getCheckedRadioButtonId() == -1 && rdgCommercial.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getContext(), "Please Check on option", Toast.LENGTH_SHORT).show();
            } else {

                if (rdgPrivate.getCheckedRadioButtonId() != -1) {
                    if (rdgPrivate.getCheckedRadioButtonId() == R.id.rd_City){
                        building = "private_1";
                    }
                    if (rdgPrivate.getCheckedRadioButtonId() == R.id.rd_upcountry){
                        building = "private_2";
                    }
                    if (rdgPrivate.getCheckedRadioButtonId() == R.id.rd_ghetto){
                        building = "private_3";
                    }
                    if (rdgPrivate.getCheckedRadioButtonId() == R.id.rd_estate){
                        building = "private_4";
                    }

                    AddBuilding1FragmentDirections.NavigateToAddBuilding2 action = AddBuilding1FragmentDirections.navigateToAddBuilding2(building);
                    Navigation.findNavController(view).navigate(action);
                } else {
                    if (rdgCommercial.getCheckedRadioButtonId() == R.id.rd_hotel){
                        building = "commercial_1";
                    }
                    if (rdgCommercial.getCheckedRadioButtonId() == R.id.rd_rental){
                        building = "commercial_2";
                    }
                    if (rdgCommercial.getCheckedRadioButtonId() == R.id.rd_industrial){
                        building = "commercial_3";
                    }
                    if (rdgCommercial.getCheckedRadioButtonId() == R.id.rd_office){
                        building = "commercial_4";
                    }

                    AddBuilding1FragmentDirections.NavigateToAddBuilding2 action = AddBuilding1FragmentDirections.navigateToAddBuilding2(building);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        }
    };

    private View.OnClickListener btnSelectListener = new View.OnClickListener() {
        boolean visible;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add_private:
                    setFocus(btn_unfocus, btn[0]);
                    TransitionManager.beginDelayedTransition(lytAddBuildingData);
                    visible = !visible;
                    lytPrivate.setVisibility(visible ? View.VISIBLE : View.VISIBLE);
                    lytCommercial.setVisibility(visible ? View.GONE : View.GONE);

                    rdgPrivate.clearCheck();
                    rdgCommercial.clearCheck();

                    break;

                case R.id.btn_add_commercial:
                    setFocus(btn_unfocus, btn[1]);
                    TransitionManager.beginDelayedTransition(lytAddBuildingData);
                    visible = !visible;
                    lytCommercial.setVisibility(visible ? View.VISIBLE : View.VISIBLE);
                    lytPrivate.setVisibility(visible ? View.GONE : View.GONE);

                    rdgPrivate.clearCheck();
                    rdgCommercial.clearCheck();
                    break;
            }
        }
    };

    private void setFocus(Button btn_unfocus, Button btn_focus) {
        Drawable unselectedBtn = getContext().getDrawable(R.drawable.building_unselected);
        Drawable selectedBtn = getContext().getDrawable(R.drawable.building_selected);

        btn_unfocus.setTextColor(Color.rgb(0, 0, 0));
        btn_unfocus.setBackground(unselectedBtn);
        btn_focus.setBackground(selectedBtn);
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        this.btn_unfocus = btn_focus;
    }

    private void btnToggle(View.OnClickListener listener) {
        for (int i = 0; i < btn.length; i++) {
            btn[i] = (Button) view.findViewById(btn_id[i]);
            btn[i].setOnClickListener(listener);
        }
        btn_unfocus = btn[0];
    }
}