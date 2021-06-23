package com.project.buildingapp.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.buildingapp.R;
import com.project.buildingapp.models.Building;
import com.project.buildingapp.models.CareTaker;
import com.project.buildingapp.models.Session;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

public class AddBuilding4Fragment extends Fragment {

    private View view;
    private EditText txtArchitectName, txtSupervisorName, txtContractorName;
    private ProgressBar progressBar;
    private Button btnAddBuilding4;

    private String email;
    private String buildingname, buildingtype, buildingcounty, buildingtown, buildingdescription, buildingurl;
    private String caretakername, caretakeremail;
    private int caretakerphone;
    private int maxid = 0;
    private Uri uri;

    private AddBuilding4FragmentArgs args;

    private DatabaseReference reference, caretakerrefence, sessionreference;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_building4, container, false);

        // set
        ((ToolBarLocker)getActivity()).ToolBarLocked(false);
        ((BottomNavLocker)getActivity()).BottomNavLocked(true);

        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        reference = FirebaseDatabase.getInstance().getReference().child("table_building");
        caretakerrefence = FirebaseDatabase.getInstance().getReference().child("table_caretaker");
        sessionreference = FirebaseDatabase.getInstance().getReference().child("table_session");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        args = AddBuilding4FragmentArgs.fromBundle(getArguments());

        buildingname = args.getBuildingName();
        buildingtype = args.getBuildingType();
        buildingcounty = args.getBuildingCounty();
        buildingtown = args.getBuildingTown();
        buildingdescription = args.getBuildingDescription();
        buildingurl = args.getBuildingUrl();
        uri = Uri.parse(buildingurl);

        caretakeremail = args.getCaretakerEmail();
        caretakername = args.getCaretakerName();
        caretakerphone = args.getCaretakerPhone();

        // find view by id
        txtArchitectName = view.findViewById(R.id.txt_architectname);
        txtSupervisorName = view.findViewById(R.id.txt_supervisorname);
        txtContractorName = view.findViewById(R.id.txt_contractorname);
        progressBar = view.findViewById(R.id.progressbar_addbuilding);
        btnAddBuilding4 = view.findViewById(R.id.btn_add_building4);

        // set / load
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxid = (int) (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // listeners
        btnAddBuilding4.setOnClickListener(addBuildingListener);

        return view;
    }


    private View.OnClickListener addBuildingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String architectname = txtArchitectName.getText().toString().trim();
            String contractorname = txtContractorName.getText().toString().trim();
            String supervisorname = txtSupervisorName.getText().toString().trim();

            if (architectname.isEmpty()) {
                txtArchitectName.setError("Text Field is empty");
                txtArchitectName.setFocusable(true);
            } else if (contractorname.isEmpty()) {
                txtArchitectName.setError(null);
                txtContractorName.setError("Text Field is empty");
                txtContractorName.setFocusable(true);
            } else if (supervisorname.isEmpty()) {
                txtArchitectName.setError(null);
                txtContractorName.setError(null);
                txtSupervisorName.setError("Text Field is empty");
                txtSupervisorName.setFocusable(true);
            } else {
                txtArchitectName.setError(null);
                txtContractorName.setError(null);
                txtSupervisorName.setError(null);

                saveImage();
            }
        }
    };


    private void saveImage() {
        if (uri != null) {
            uploadToFirebase(uri);
        } else {
            Toast.makeText(getContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }


    private void uploadToFirebase(Uri uri) {
        String buildingcode = String.valueOf(maxid + 1000);
        ProgressDialog pd = new ProgressDialog(getContext());
        final StorageReference ref = storageReference.child("BuildingImages/" + System.currentTimeMillis() + "." + getFileExtension(uri));

        pd.setTitle("Building Data");
        pd.setMessage("Updating data....");

        pd.show();

        ref.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Building building = new Building(email,
                                email + "_" + buildingcode,
                                buildingcode,
                                buildingtype,
                                buildingname,
                                buildingcounty,
                                buildingtown,
                                buildingdescription,
                                uri.toString());

                        reference.push().setValue(building).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    CareTaker careTaker = new CareTaker(
                                            email,
                                            email + "_" + caretakeremail,
                                            caretakeremail,
                                            caretakername,
                                            caretakerphone
                                    );

                                    caretakerrefence.push().setValue(careTaker).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Session session = new Session(
                                                        email,
                                                        buildingcode,
                                                        email + "_" + String.valueOf(true),
                                                        true
                                                );

                                                sessionreference.push().setValue(session).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            pd.dismiss();
                                                            Navigation.findNavController(view).navigate(AddBuilding4FragmentDirections.navigateToHomeFromAddBuilding3());
                                                            Toast.makeText(getContext(), "Successfull", Toast.LENGTH_LONG).show();
                                                        } else {
                                                            Toast.makeText(getContext(), "Error uploading data", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(getContext(), "Error Uploading data", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getContext(), "Error uploading data", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "Error in uploading", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getContext(), "Uploading failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = (getActivity()).getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
}