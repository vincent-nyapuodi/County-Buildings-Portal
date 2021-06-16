package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.buildingapp.R;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

import de.hdodenhof.circleimageview.CircleImageView;


public class DocumentsFragment extends Fragment {

    // TODO: Two versions view/edit (upload) and view only ()

    private View view;

    private RelativeLayout lytKraHeading, lytNemaHeading, lytSanitationHeading, lytFireSafetyHeading, lytInspectionHeading; // TODO : transition on click
    private LinearLayout lytKraBody, lytNemaBody, lytSanitationBody, lytFireSafetyBody, lytInspectionBody, lytData; // TODO : make visible on click header
    private TextView tvKraContext, tvNemaContext, tvSanitationContext, tvFireSafetyContext, tvInspectionContext;  // TODO : made visible on upload / approve
    private ImageView imgKraPlus, imgNemaPlus, imgSanitationPlus, imgFireSafetyPlus, imgInspectionPlus; // TODO : switch btn minus and plus
    private ImageView imgKraUploadDoc, imgNemaUploadDoc, imgSanitationUploadDoc, imgFireSafetyUploadDoc, imgInspectionUploadDoc;
    private TextView tvKraUploadDoc, tvNemaUploadDoc, tvSanitationUploadDoc, tvFireSafetyUploadDoc, tvInspectionUploadDoc;
    private EditText txtKraCertification, txtNemaCertification, txtSanitationCertification, txtFireSafetyCertification, txtInspectionCertification;
    private Button btnKraUpload, btnNemaUpload, btnSanitationUpload, btnFireSafetyUpload, btnInspectionUpload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_documents, container, false);

        // set
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);


        // find view by id
        lytKraHeading = view.findViewById(R.id.lyt_heading_kracertification);
        lytNemaHeading = view.findViewById(R.id.lyt_heading_nemacertification);
        lytSanitationHeading = view.findViewById(R.id.lyt_heading_sanitationcertification);
        lytFireSafetyHeading = view.findViewById(R.id.lyt_heading_firesafetycertification);
        lytInspectionHeading = view.findViewById(R.id.lyt_heading_inspectioncertification);

        lytKraBody = view.findViewById(R.id.lyt_body_kracertification);
        lytNemaBody = view.findViewById(R.id.lyt_body_nemacertification);
        lytSanitationBody = view.findViewById(R.id.lyt_body_sanitationcertification);
        lytFireSafetyBody = view.findViewById(R.id.lyt_body_firesafetycertification);
        lytInspectionBody = view.findViewById(R.id.lyt_body_inspectioncertification);
        lytData = view.findViewById(R.id.lyt_certifications_data);

        tvKraContext = view.findViewById(R.id.tv_kra_context);
        tvNemaContext = view.findViewById(R.id.tv_nema_context);
        tvSanitationContext = view.findViewById(R.id.tv_sanitation_context);
        tvFireSafetyContext = view.findViewById(R.id.tv_firesafety_context);
        tvInspectionContext = view.findViewById(R.id.tv_inspection_context);

        imgKraPlus = view.findViewById(R.id.img_kra_plus);
        imgNemaPlus = view.findViewById(R.id.img_nema_plus);
        imgSanitationPlus = view.findViewById(R.id.img_sanitation_plus);
        imgFireSafetyPlus = view.findViewById(R.id.img_firesafety_plus);
        imgInspectionPlus = view.findViewById(R.id.img_inspection_plus);

        imgKraUploadDoc = view.findViewById(R.id.img_kra_uploaddoc);
        imgNemaUploadDoc = view.findViewById(R.id.img_nema_uploaddoc);
        imgSanitationUploadDoc = view.findViewById(R.id.img_sanitation_uploaddoc);
        imgFireSafetyUploadDoc = view.findViewById(R.id.img_firesafety_uploaddoc);
        imgInspectionUploadDoc = view.findViewById(R.id.img_inspection_uploaddoc);

        tvKraUploadDoc = view.findViewById(R.id.tv_kra_uploaddoc);
        tvNemaUploadDoc = view.findViewById(R.id.tv_nema_uploaddoc);
        tvSanitationUploadDoc = view.findViewById(R.id.tv_sanitation_uploaddoc);
        tvFireSafetyUploadDoc = view.findViewById(R.id.tv_firesafety_uploaddoc);
        tvInspectionUploadDoc = view.findViewById(R.id.tv_inspection_uploaddoc);

        txtKraCertification = view.findViewById(R.id.txt_kra_certificateno);
        txtNemaCertification = view.findViewById(R.id.txt_nema_certificateno);
        txtSanitationCertification = view.findViewById(R.id.txt_sanitation_certificateno);
        txtFireSafetyCertification = view.findViewById(R.id.txt_firesafety_certificateno);
        txtInspectionCertification = view.findViewById(R.id.txt_inspection_certificateno);

        btnKraUpload = view.findViewById(R.id.btn_kra_upload);
        btnNemaUpload = view.findViewById(R.id.btn_nema_upload);
        btnSanitationUpload = view.findViewById(R.id.btn_sanitation_upload);
        btnFireSafetyUpload = view.findViewById(R.id.btn_firesafety_upload);
        btnInspectionUpload = view.findViewById(R.id.btn_inspection_upload);

        // set / load data


        // listeners
        imgKraPlus.setOnClickListener(kraVisibilityListener);
        imgNemaPlus.setOnClickListener(nemaVisibilityListener);
        imgSanitationPlus.setOnClickListener(sanitationVisibilityListener);
        imgFireSafetyPlus.setOnClickListener(fireSafetyVisibilityListener);
        imgInspectionPlus.setOnClickListener(inspectionVisibilityListener);

        btnKraUpload.setOnClickListener(kraListener);
        btnNemaUpload.setOnClickListener(nemaListener);
        btnSanitationUpload.setOnClickListener(sanitationListener);
        btnFireSafetyUpload.setOnClickListener(fireSafetyListener);
        btnInspectionUpload.setOnClickListener(inspectionListener);

        return view;
    }

    private View.OnClickListener kraListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener nemaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener sanitationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener fireSafetyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener inspectionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    // visibility
    private View.OnClickListener kraVisibilityListener = new View.OnClickListener() {
        boolean visible;

        @Override
        public void onClick(View view) {

            TransitionManager.beginDelayedTransition(lytData);
            visible = !visible;
            imgKraPlus.setImageResource(visible ? R.drawable.ic_baseline_minus_24 : R.drawable.ic_baseline_add_24);
            lytKraBody.setVisibility(visible ? View.VISIBLE : View.GONE);

        }
    };

    private View.OnClickListener nemaVisibilityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener sanitationVisibilityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener fireSafetyVisibilityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener inspectionVisibilityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}