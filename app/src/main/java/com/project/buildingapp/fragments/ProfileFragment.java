package com.project.buildingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.buildingapp.R;
import com.project.buildingapp.models.Building;
import com.project.buildingapp.models.Contractor;
import com.project.buildingapp.models.Owner;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;


public class ProfileFragment extends Fragment {

    private View view;

    private TextView tvOwnerName, tvOwnerPhone, tvBuildingName, tvBuildingType, tvBuildingLocation, tvBuildingDesc, tvBuildingArchitect, tvBuildingSupervisor, tvBuildingContractor;

    private String buildingcode, buildingrefkey, buildingname, buildingtype, buildinglocation, buildingdetails, buildingemail;

    private ProfileFragmentArgs args;

    private DatabaseReference buildingreference, ownerreference, contractorreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        // set
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);
        ((ToolBarLocker) getActivity()).ToolBarLocked(false);

        args = ProfileFragmentArgs.fromBundle(getArguments());

        buildingrefkey = args.getBuildingRefKey();
        buildingcode = args.getBuildingCode();

        buildingreference = FirebaseDatabase.getInstance().getReference().child("table_building");
        ownerreference = FirebaseDatabase.getInstance().getReference().child("table_owner");
        contractorreference = FirebaseDatabase.getInstance().getReference().child("table_contractor");

        // find view by id
        tvOwnerName = view.findViewById(R.id.tv_ownername);
        tvOwnerPhone = view.findViewById(R.id.tv_ownerphone);

        tvBuildingName = view.findViewById(R.id.tv_buildingname);
        tvBuildingType = view.findViewById(R.id.tv_buildingresidence);
        tvBuildingLocation = view.findViewById(R.id.tv_buildinglocation);
        tvBuildingDesc = view.findViewById(R.id.tv_buildingdescription);

        tvBuildingArchitect = view.findViewById(R.id.tv_buildingarchitect);
        tvBuildingSupervisor = view.findViewById(R.id.tv_buildingsupervisor);
        tvBuildingContractor = view.findViewById(R.id.tv_buildingcontractor);

        // set / load data
        loadData();

        // listener

        return view;
    }

    private void loadData() {
        buildingreference.orderByKey().equalTo(buildingrefkey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    Building building = snapshot.getValue(Building.class);

                    buildingname = building.getBuildingname();
                    buildingtype = building.getBuildingtype();
                    buildinglocation = building.getBuildingcounty() + " County - " + building.getBuildingtown();
                    buildingdetails = building.getBuildingdesc();
                    buildingemail = building.getOwneremail();

                    String [] array = buildingtype.split("_");
                    String residencetype = array[0].toString();

                    String type = "";

                    switch (buildingtype) {
                        case "private_1":
                            type = "city";
                            break;
                        case "private_2":
                            type = "upcountry";
                            break;
                        case "private_3":
                            type = "ghetto";
                            break;
                        case "private_4":
                            type = "estate";
                            break;
                        case "commercial_1":
                            type = "hotel";
                            break;
                        case "commercial_2":
                            type = "rental";
                            break;
                        case "commercial_3":
                            type = "industrial";
                            break;
                        case "commercial_4":
                            type = "office";
                            break;
                    }

                    tvBuildingName.setText(buildingname);
                    tvBuildingLocation.setText(buildinglocation);
                    tvBuildingType.setText(residencetype + " residence | " + type);
                    tvBuildingDesc.setText(buildingdetails);
                }

                ownerreference.orderByChild("email").equalTo(buildingemail).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot != null) {
                            Owner owner = snapshot.getValue(Owner.class);

                            tvOwnerName.setText(owner.getName());
                            tvOwnerPhone.setText("0" + String.valueOf(owner.getPhone()));
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

        contractorreference.orderByChild("buildingcode").equalTo(buildingcode).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    Contractor contractor = snapshot.getValue(Contractor.class);

                    tvBuildingArchitect.setText(contractor.getArchitectname());
                    tvBuildingSupervisor.setText(contractor.getSupervisorname());
                    tvBuildingContractor.setText(contractor.getContractorname());
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