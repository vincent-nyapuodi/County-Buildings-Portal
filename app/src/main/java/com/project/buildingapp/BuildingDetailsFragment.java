package com.project.buildingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.buildingapp.models.Approval;
import com.project.buildingapp.models.Building;
import com.project.buildingapp.models.Certifications;
import com.project.buildingapp.utils.BottomNavLocker;

public class BuildingDetailsFragment extends Fragment {

    private View view;

    private ImageView imgBuilding;
    private TextView tvBuildingName, tvBuildingLocation, tvProfileSeeMore;
    private TextView tvKraCertification, tvNemaCertification, tvFireSafetyCertification, tvSanitationCertification, tvInspectionCertification;
    private TextView tvKraContext, tvNemaContext, tvFireSafetyContext, tvSanitationContext, tvInspectionContext;
    private ImageView imgKra, imgNema, imgFireSafety, imgSanitation, imgInspection;

    private String buildingrefkey, buildingcode, buildingname, buildinglocation, buildingimage, buildingemail, countyname;
    private int status;

    private BuildingDetailsFragmentArgs args;

    private DatabaseReference buildingreference, certificationreference, approvalreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_building_details, container, false);

        // set
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);

        args = BuildingDetailsFragmentArgs.fromBundle(getArguments());

        buildingrefkey = args.getBuildingRefKey();
        buildingcode = args.getBuildingCode();

        buildingreference = FirebaseDatabase.getInstance().getReference().child("table_building");
        certificationreference = FirebaseDatabase.getInstance().getReference().child("table_certification");
        approvalreference = FirebaseDatabase.getInstance().getReference().child("table_approval");

        // find view by id
        tvBuildingName = view.findViewById(R.id.tv_profile_buildingname);
        tvBuildingLocation = view.findViewById(R.id.tv_profile_buildinglocation);
        tvProfileSeeMore = view.findViewById(R.id.tv_profile_seemore);
        imgBuilding = view.findViewById(R.id.img_building);

        tvKraCertification = view.findViewById(R.id.tv_certifications_kra);
        tvNemaCertification = view.findViewById(R.id.tv_certifications_nema);
        tvFireSafetyCertification = view.findViewById(R.id.tv_certifications_firesafety);
        tvSanitationCertification = view.findViewById(R.id.tv_certifications_sanitation);
        tvInspectionCertification = view.findViewById(R.id.tv_certifications_inspection);

        tvKraContext = view.findViewById(R.id.tv_kra_context);
        tvNemaContext = view.findViewById(R.id.tv_nema_context);
        tvFireSafetyContext = view.findViewById(R.id.tv_firesafety_context);
        tvSanitationContext = view.findViewById(R.id.tv_sanitation_context);
        tvInspectionContext = view.findViewById(R.id.tv_inspection_context);

        imgKra = view.findViewById(R.id.img_certifications_kraverification);
        imgNema = view.findViewById(R.id.img_certifications_nemaverification);
        imgFireSafety = view.findViewById(R.id.img_certifications_firesafetyverification);
        imgSanitation = view.findViewById(R.id.img_certifications_sanitationverification);
        imgInspection = view.findViewById(R.id.img_certifications_inspectionverification);


        // set / load data
        loadData();

        // listener
        tvProfileSeeMore.setOnClickListener(seemoreListener);

        return view;
    }


    private View.OnClickListener seemoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };


    private void loadData() {
        buildingreference.orderByKey().equalTo(buildingrefkey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    Building building = snapshot.getValue(Building.class);

                    buildingname = building.getBuildingname();
                    buildinglocation = building.getBuildingtown() + ", " + building.getBuildingcounty();
                    buildingimage = building.getBuildingphoto();
                    buildingcode = building.getBuildingcode();
                    buildingemail = building.getOwneremail();
                }

                tvBuildingName.setText(buildingname);
                tvBuildingLocation.setText(buildinglocation);
                Glide.with(getContext()).load(buildingimage).into(imgBuilding);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        loadCertificationData(certificationreference, buildingcode, "kra", imgKra);
        loadCertificationData(certificationreference, buildingcode, "nema", imgNema);
        loadCertificationData(certificationreference, buildingcode, "firesafety", imgFireSafety);
        loadCertificationData(certificationreference, buildingcode, "sanitation", imgSanitation);
        loadCertificationData(certificationreference, buildingcode, "inspection", imgInspection);

        loadApprovalData(approvalreference, buildingcode, "kra", tvKraContext);
        loadApprovalData(approvalreference, buildingcode, "nema", tvNemaContext);
        loadApprovalData(approvalreference, buildingcode, "firesafety", tvFireSafetyContext);
        loadApprovalData(approvalreference, buildingcode, "sanitation", tvSanitationContext);
        loadApprovalData(approvalreference, buildingcode, "inspection", tvInspectionContext);
    }

    private void loadCertificationData(DatabaseReference reference, String buildingcode, String certification, ImageView imageView) {
        reference.orderByChild("buildingcode_certificate").equalTo(buildingcode + "_" + certification).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    Certifications certifications = snapshot.getValue(Certifications.class);

                    status = certifications.getStatus();

                    if (status == 1) {
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadApprovalData(DatabaseReference reference, String buildingcode, String certificate, TextView textView) {
        reference.orderByChild("buildingcode_certificate").equalTo(buildingcode + "_" + certificate).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    Approval approval = snapshot.getValue(Approval.class);

                    countyname = approval.getCountyname();

                    textView.setText("Approved by " + countyname + " county government");
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}