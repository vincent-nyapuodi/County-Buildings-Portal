package com.project.buildingapp.fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.buildingapp.R;
import com.project.buildingapp.models.Approval;
import com.project.buildingapp.models.Building;
import com.project.buildingapp.models.Certifications;
import com.project.buildingapp.models.PublicComments;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Timestamp;

public class BuildingDetailsFragment extends Fragment {

    private View view;

    private ImageView imgBuilding;
    private TextView tvBuildingName, tvBuildingLocation, tvProfileSeeMore;
    private TextView tvKraCertification, tvNemaCertification, tvFireSafetyCertification, tvSanitationCertification, tvInspectionCertification;
    private TextView tvKraContext, tvNemaContext, tvFireSafetyContext, tvSanitationContext, tvInspectionContext;
    private ImageView imgKra, imgNema, imgFireSafety, imgSanitation, imgInspection;
    private TextView tvComment, tvViewComments;
    private ImageView imgDownloadKra, imgDownloadNema, imgDownloadFireSafety, imgDownloadSanitation, imgDownloadInspection;

    private String buildingrefkey, buildingcode, buildingname, buildinglocation, buildingimage, buildingemail, countyname, email, time;
    private int status;

    private FirebaseUser user;
    private BuildingDetailsFragmentArgs args;

    private DatabaseReference buildingreference, certificationreference, approvalreference, commentreference;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_building_details, container, false);

        // set
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);
        ((ToolBarLocker) getActivity()).ToolBarLocked(false);

        args = BuildingDetailsFragmentArgs.fromBundle(getArguments());

        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        time = timestamp.toString();

        buildingrefkey = args.getBuildingRefKey();
        buildingcode = args.getBuildingCode();

        buildingreference = FirebaseDatabase.getInstance().getReference().child("table_building");
        certificationreference = FirebaseDatabase.getInstance().getReference().child("table_certification");
        approvalreference = FirebaseDatabase.getInstance().getReference().child("table_approval");
        commentreference = FirebaseDatabase.getInstance().getReference().child("table_public_comments");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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

        imgDownloadKra = view.findViewById(R.id.img_kra_download);
        imgDownloadNema = view.findViewById(R.id.img_nema_download);
        imgDownloadFireSafety = view.findViewById(R.id.img_firesafety_download);
        imgDownloadSanitation = view.findViewById(R.id.img_sanitation_download);
        imgDownloadInspection = view.findViewById(R.id.img_inspection_download);

        tvComment = view.findViewById(R.id.tv_user_comment);
        tvViewComments = view.findViewById(R.id.tv_user_viewcomments);

        // set / load data
        loadData();

        // listener
        tvProfileSeeMore.setOnClickListener(seemoreListener);
        imgDownloadKra.setOnClickListener(kraListener);
        imgDownloadNema.setOnClickListener(kraListener);
        imgDownloadFireSafety.setOnClickListener(firesafetyListener);
        imgDownloadSanitation.setOnClickListener(sanitationListener);
        imgDownloadInspection.setOnClickListener(inspectionListener);

        tvComment.setOnClickListener(commentListener);
        tvViewComments.setOnClickListener(viewCommentsListener);

        return view;
    }


    private View.OnClickListener seemoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BuildingDetailsFragmentDirections.ActionBuildingDetailsFragmentToProfileFragment action
                    = BuildingDetailsFragmentDirections.actionBuildingDetailsFragmentToProfileFragment(buildingrefkey, buildingcode);
            Navigation.findNavController(view).navigate(action);
        }
    };

    private View.OnClickListener commentListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("Feedback");
            final View customLayout = requireActivity().getLayoutInflater().inflate(R.layout.list_comment, null);

            Spinner spinner = customLayout.findViewById(R.id.spinner_commenttype);
            EditText txtComment = customLayout.findViewById(R.id.txt_comment);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.comment_type, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            builder.setView(customLayout)
                    .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            String commenttext = txtComment.getText().toString().trim();
                            String commenttype = spinner.getSelectedItem().toString();

                            if (commenttext.isEmpty()) {
                                Toast.makeText(getContext(), "Text field is empty", Toast.LENGTH_SHORT).show();
                            } else if (commenttype.equals("-- SELECT FEEDBACK TYPE --")) {
                                Toast.makeText(getContext(), "Select Comment type", Toast.LENGTH_SHORT).show();
                            } else {
                                PublicComments comments = new PublicComments(
                                        buildingcode,
                                        email,
                                        buildingcode + "_" + email,
                                        commenttype,
                                        commenttext,
                                        time
                                );

                                commentreference.push().setValue(comments).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getContext(), "Error sending data", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                });
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    private View.OnClickListener viewCommentsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BuildingDetailsFragmentDirections.NavigateToUsersComments action
                    = BuildingDetailsFragmentDirections.navigateToUsersComments(buildingcode);
            NavHostFragment.findNavController(getParentFragment()).navigate(action);
        }
    };


    private View.OnClickListener kraListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            download(buildingcode, "kra");
        }
    };

    private View.OnClickListener nemaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            download(buildingcode, "nema");
        }
    };

    private View.OnClickListener sanitationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            download(buildingcode, "sanitation");
        }
    };

    private View.OnClickListener firesafetyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            download(buildingcode, "firesafety");
        }
    };

    private View.OnClickListener inspectionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            download(buildingcode, "inspection");
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

    private void loadCertificationData(DatabaseReference reference, String
            buildingcode, String certification, ImageView imageView) {
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

    private void loadApprovalData(DatabaseReference reference, String buildingcode, String
            certificate, TextView textView) {
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


    private void download(String buildingcode, String certificate) {

        final AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle("Download");
        b.setMessage("Are you sure?");
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                certificationreference.orderByChild("buildingcode_certificate").equalTo(buildingcode + "_" + certificate).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String url = "";

                        if (snapshot != null) {


                            Certifications certifications = snapshot.getValue(Certifications.class);

                            url = certifications.getCertificateurl();

                            storageReference.child("BuildingCertifications/" + buildingcode + "/" + getFilename(Uri.parse(url))).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadFile(getContext(), buildingcode + "_" + certificate + " certificate", "pdf", "/BuildingCertifications", uri.toString());

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Download successful");
                                    builder.setMessage(buildingcode + "_" + certificate + " certificate" +".pdf");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Error message : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "No file to be downloaded", Toast.LENGTH_SHORT).show();
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
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog d = b.create();
        d.show();
    }

    public long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {


        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        return downloadmanager.enqueue(request);
    }

    private String getFilename(Uri uri) {
        File file = new File(uri.getPath());
        String displayName = file.getName();

        return displayName;
    }
}