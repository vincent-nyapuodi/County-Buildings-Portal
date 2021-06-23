package com.project.buildingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.buildingapp.R;
import com.project.buildingapp.models.Building;
import com.project.buildingapp.models.Session;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;
import com.shuhart.stepview.StepView;

public class HomeFragment extends Fragment {

    private View view;
    
    private TextView tvBuildingName, tvBuildingLocation, tvBuildingApproved, tvDocumentProgress, tvApprovalProgress, tvApprovalView;
    private Button btnUploadDocuments;
    private StepView stepViewApproval;

    private String email, buildingcode, location, buildingname;

    private FirebaseUser user;
    private DatabaseReference reference, sessionreference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // set
        ((ToolBarLocker)getActivity()).ToolBarLocked(false);
        ((BottomNavLocker)getActivity()).BottomNavLocked(false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        Toast.makeText(getContext(), "Email = " + email, Toast.LENGTH_SHORT).show();

        reference = FirebaseDatabase.getInstance().getReference().child("table_building");
        sessionreference = FirebaseDatabase.getInstance().getReference().child("table_session");

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
        loadBuildingData();


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


    private void loadBuildingData() {
        sessionreference.orderByChild("email_status").equalTo(email + "_true").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    Session session = snapshot.getValue(Session.class);
                    buildingcode = session.getBuildingcode();
                }

                reference.orderByChild("buildingcode").equalTo(buildingcode).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot != null) {
                            Building building = snapshot.getValue(Building.class);

                            buildingname = building.getBuildingname();
                            location = building.getBuildingtown() + ", " + building.getBuildingcounty();
                        }

                        tvBuildingName.setText(buildingname);
                        tvBuildingLocation.setText(location);
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
    }
}