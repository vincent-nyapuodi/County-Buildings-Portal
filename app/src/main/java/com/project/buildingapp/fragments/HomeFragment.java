package com.project.buildingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;
import com.shuhart.stepview.StepView;

public class HomeFragment extends Fragment {

    private View view;
    
    private TextView tvBuildingName, tvBuildingLocation, tvBuildingApproved, tvDocumentProgress, tvApprovalProgress, tvApprovalView;
    private Button btnUploadDocuments;
    private StepView stepViewApproval;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // set
        ((ToolBarLocker)getActivity()).ToolBarLocked(false);
        ((BottomNavLocker)getActivity()).BottomNavLocked(false);

        // find view by id
        tvBuildingName = view.findViewById(R.id.tv_buildingname);
        tvBuildingLocation = view.findViewById(R.id.tv_buildinglocation);
        tvBuildingApproved = view.findViewById(R.id.tv_buildingapproved);
        tvDocumentProgress = view.findViewById(R.id.tv_documentsprogress);
        tvApprovalProgress = view.findViewById(R.id.tv_approvalprogress);
        tvApprovalView = view.findViewById(R.id.tv_approvalview);
        btnUploadDocuments = view.findViewById(R.id.btn_upload_documents);
        stepViewApproval = view.findViewById(R.id.stepview_approval);

        // set / load data


        // listeners
        btnUploadDocuments.setOnClickListener(uploadListener);
        tvApprovalView.setOnClickListener(approveListener);

        return view;
    }


    private View.OnClickListener uploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.navigateToDocuments);
        }
    };

    private View.OnClickListener approveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}