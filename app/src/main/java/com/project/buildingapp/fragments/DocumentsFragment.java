package com.project.buildingapp.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.project.buildingapp.models.Certifications;
import com.project.buildingapp.models.Session;
import com.project.buildingapp.utils.BottomNavLocker;
import com.project.buildingapp.utils.ToolBarLocker;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class DocumentsFragment extends Fragment {

    // TODO: Two versions view/edit (upload) and view only ()

    private View view;

    private RelativeLayout lytKraHeading, lytNemaHeading, lytSanitationHeading, lytFireSafetyHeading, lytInspectionHeading; // TODO : transition on click
    private LinearLayout lytKraBody, lytNemaBody, lytSanitationBody, lytFireSafetyBody, lytInspectionBody, lytData; // TODO : make visible on click header
    private TextView tvKraContext, tvNemaContext, tvSanitationContext, tvFireSafetyContext, tvInspectionContext;  // TODO : made visible on upload / approve
    private TextView tvKraFeedback, tvNemaFeedback, tvSanitationFeedback, tvFireSafetyFeedback, tvInspectionFeedback;  // TODO : made visible on upload / approve
    private ImageView imgKraPlus, imgNemaPlus, imgSanitationPlus, imgFireSafetyPlus, imgInspectionPlus; // TODO : switch btn minus and plus
    private ImageView imgKraUploadDoc, imgNemaUploadDoc, imgSanitationUploadDoc, imgFireSafetyUploadDoc, imgInspectionUploadDoc;
    private TextView tvKraUploadDoc, tvNemaUploadDoc, tvSanitationUploadDoc, tvFireSafetyUploadDoc, tvInspectionUploadDoc, tvPublicComments;
    private EditText txtKraCertification, txtNemaCertification, txtSanitationCertification, txtFireSafetyCertification, txtInspectionCertification;
    private Button btnKraUpload, btnNemaUpload, btnSanitationUpload, btnFireSafetyUpload, btnInspectionUpload;

    private String email, buildingcode, chooser;
    private int kra, nema, sanitation, firesafety, inspection;
    private Uri chooserurl = null, kraurl, nemaurl, sanitationurl, firesafetyurl, inspectionurl;
    private int SELECT_PDF = 200;

    private FirebaseUser user;
    private DatabaseReference reference, sessionreference, feedbackreference;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_documents, container, false);

        // set
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);

        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        sessionreference = FirebaseDatabase.getInstance().getReference().child("table_session");
        reference = FirebaseDatabase.getInstance().getReference().child("table_certification");
        feedbackreference = FirebaseDatabase.getInstance().getReference().child("table_comments");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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

        tvKraFeedback = view.findViewById(R.id.tv_kra_feedback);
        tvNemaFeedback = view.findViewById(R.id.tv_nema_feedback);
        tvSanitationFeedback = view.findViewById(R.id.tv_sanitation_feedback);
        tvFireSafetyFeedback = view.findViewById(R.id.tv_firesafety_feedback);
        tvInspectionFeedback = view.findViewById(R.id.tv_inspection_feedback);

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

        tvPublicComments = view.findViewById(R.id.tv_certifications_public_comments);

        // set / load data
        loadData();

        // listeners
        imgKraPlus.setOnClickListener(kraVisibilityListener);
        imgNemaPlus.setOnClickListener(nemaVisibilityListener);
        imgSanitationPlus.setOnClickListener(sanitationVisibilityListener);
        imgFireSafetyPlus.setOnClickListener(fireSafetyVisibilityListener);
        imgInspectionPlus.setOnClickListener(inspectionVisibilityListener);

        imgKraUploadDoc.setOnClickListener(kraUploadListener);
        imgNemaUploadDoc.setOnClickListener(nemaUploadListener);
        imgFireSafetyUploadDoc.setOnClickListener(fireSafetyUploadListener);
        imgSanitationUploadDoc.setOnClickListener(sanitationUploadListener);
        imgInspectionUploadDoc.setOnClickListener(inspectionUploadListener);

        btnKraUpload.setOnClickListener(kraListener);
        btnNemaUpload.setOnClickListener(nemaListener);
        btnSanitationUpload.setOnClickListener(sanitationListener);
        btnFireSafetyUpload.setOnClickListener(fireSafetyListener);
        btnInspectionUpload.setOnClickListener(inspectionListener);

        tvKraFeedback.setOnClickListener(kraFeedbackListener);
        tvSanitationFeedback.setOnClickListener(nemaFeedbackListener);
        tvFireSafetyFeedback.setOnClickListener(firesafetyFeedbackListener);
        tvSanitationFeedback.setOnClickListener(sanitationFeedbackListener);
        tvInspectionFeedback.setOnClickListener(inspectionFeedbackListener);

        tvPublicComments.setOnClickListener(publicCommentsListener);

        return view;
    }

    private View.OnClickListener publicCommentsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DocumentsFragmentDirections.NavigateToPublicComments action
                    = DocumentsFragmentDirections.navigateToPublicComments(buildingcode);
            NavHostFragment.findNavController(getParentFragment()).navigate(action);
        }
    };

    private View.OnClickListener kraListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            kraUploadData();
        }
    };

    private View.OnClickListener nemaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nemaUploadData();
        }
    };

    private View.OnClickListener sanitationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sanitationUploadData();
        }
    };

    private View.OnClickListener fireSafetyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            firesafetyUploadData();
        }
    };

    private View.OnClickListener inspectionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            inspectionUploadData();
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
            txtKraCertification.setError(visible ? null : null);
        }
    };

    private View.OnClickListener nemaVisibilityListener = new View.OnClickListener() {
        boolean visible;

        @Override
        public void onClick(View view) {
            TransitionManager.beginDelayedTransition(lytData);
            visible = !visible;
            imgNemaPlus.setImageResource(visible ? R.drawable.ic_baseline_minus_24 : R.drawable.ic_baseline_add_24);
            lytNemaBody.setVisibility(visible ? View.VISIBLE : View.GONE);
            txtNemaCertification.setError(visible ? null : null);
        }
    };

    private View.OnClickListener sanitationVisibilityListener = new View.OnClickListener() {
        boolean visible;

        @Override
        public void onClick(View view) {
            TransitionManager.beginDelayedTransition(lytData);
            visible = !visible;
            imgSanitationPlus.setImageResource(visible ? R.drawable.ic_baseline_minus_24 : R.drawable.ic_baseline_add_24);
            lytSanitationBody.setVisibility(visible ? View.VISIBLE : View.GONE);
            txtSanitationCertification.setError(visible ? null : null);
        }
    };

    private View.OnClickListener fireSafetyVisibilityListener = new View.OnClickListener() {
        boolean visible;

        @Override
        public void onClick(View view) {
            TransitionManager.beginDelayedTransition(lytData);
            visible = !visible;
            imgFireSafetyPlus.setImageResource(visible ? R.drawable.ic_baseline_minus_24 : R.drawable.ic_baseline_add_24);
            lytFireSafetyBody.setVisibility(visible ? View.VISIBLE : View.GONE);
            txtFireSafetyCertification.setError(visible ? null : null);
        }
    };

    private View.OnClickListener inspectionVisibilityListener = new View.OnClickListener() {
        boolean visible;

        @Override
        public void onClick(View view) {
            TransitionManager.beginDelayedTransition(lytData);
            visible = !visible;
            imgInspectionPlus.setImageResource(visible ? R.drawable.ic_baseline_minus_24 : R.drawable.ic_baseline_add_24);
            lytInspectionBody.setVisibility(visible ? View.VISIBLE : View.GONE);
            txtInspectionCertification.setError(visible ? null : null);
        }
    };

    private View.OnClickListener kraFeedbackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DocumentsFragmentDirections.NavigateToFeedback action
                    = DocumentsFragmentDirections.navigateToFeedback(buildingcode + "_kra", "kra");
            NavHostFragment.findNavController(getParentFragment()).navigate(action);
        }
    };

    private View.OnClickListener nemaFeedbackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DocumentsFragmentDirections.NavigateToFeedback action
                    = DocumentsFragmentDirections.navigateToFeedback(buildingcode + "_nema", "nema");
            NavHostFragment.findNavController(getParentFragment()).navigate(action);
        }
    };

    private View.OnClickListener firesafetyFeedbackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DocumentsFragmentDirections.NavigateToFeedback action
                    = DocumentsFragmentDirections.navigateToFeedback(buildingcode + "_feedback", "feedback");
            NavHostFragment.findNavController(getParentFragment()).navigate(action);
        }
    };

    private View.OnClickListener sanitationFeedbackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DocumentsFragmentDirections.NavigateToFeedback action
                    = DocumentsFragmentDirections.navigateToFeedback(buildingcode + "_sanitation", "sanitation");
            NavHostFragment.findNavController(getParentFragment()).navigate(action);
        }
    };

    private View.OnClickListener inspectionFeedbackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DocumentsFragmentDirections.NavigateToFeedback action
                    = DocumentsFragmentDirections.navigateToFeedback(buildingcode + "_inspection", "inspection");
            NavHostFragment.findNavController(getParentFragment()).navigate(action);
        }
    };

    // Upload data

    private void kraUploadData() {
        uploadToFirebase("kra", txtKraCertification, tvKraContext, kraurl);
    }


    private void nemaUploadData() {
        uploadToFirebase("nema", txtNemaCertification, tvNemaContext, nemaurl);
    }

    private void firesafetyUploadData() {
        uploadToFirebase("firesafety", txtFireSafetyCertification, tvFireSafetyContext, firesafetyurl);
    }

    private void sanitationUploadData() {
        uploadToFirebase("sanitation", txtSanitationCertification, tvSanitationContext, sanitationurl);
    }

    private void inspectionUploadData() {
        uploadToFirebase("inspection", txtInspectionCertification, tvInspectionContext, inspectionurl);
    }


    // load all data

    private void loadData() {
        sessionreference.orderByChild("email_status").equalTo(email + "_true").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    Session session = snapshot.getValue(Session.class);
                    buildingcode = session.getBuildingcode();

                    kraLoad();
                    nemaLoad();
                    firesafetyLoad();
                    sanitationLoad();
                    inspectionLoad();

                    checkComments(feedbackreference, buildingcode, "kra", tvKraFeedback);
                    checkComments(feedbackreference, buildingcode, "nema", tvNemaFeedback);
                    checkComments(feedbackreference, buildingcode, "sanitation", tvSanitationFeedback);
                    checkComments(feedbackreference, buildingcode, "firesafety", tvFireSafetyFeedback);
                    checkComments(feedbackreference, buildingcode, "inspection", tvInspectionFeedback);
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


    // load data

    private void kraLoad() {
        loadFirebaseData("kra", txtKraCertification, tvKraContext);
    }

    private void nemaLoad() {
        loadFirebaseData("nema", txtNemaCertification, tvNemaContext);
    }

    private void sanitationLoad() {
        loadFirebaseData("sanitation", txtSanitationCertification, tvSanitationContext);
    }

    private void firesafetyLoad() {
        loadFirebaseData("firesafety", txtFireSafetyCertification, tvFireSafetyContext);
    }

    private void inspectionLoad() {
        loadFirebaseData("inspection", txtInspectionCertification, tvInspectionContext);
    }

    // upload listener
    private View.OnClickListener kraUploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pdfChooser("kra");
        }
    };

    private View.OnClickListener nemaUploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pdfChooser("nema");
        }
    };

    private View.OnClickListener fireSafetyUploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pdfChooser("firesafety");
        }
    };

    private View.OnClickListener sanitationUploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pdfChooser("sanitation");
        }
    };

    private View.OnClickListener inspectionUploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pdfChooser("inspection");
        }
    };


    // upload photo
    private void pdfChooser(String chooser) {

        this.chooser = chooser;

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);

        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_PDF);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_PDF && data != null && data.getData() != null) {

            this.chooserurl = data.getData();

            if (chooser == "kra") {
                kraurl = chooserurl;
            } else if (chooser == "nema") {
                nemaurl = chooserurl;
            } else if (chooser == "firesafety") {
                firesafetyurl = chooserurl;
            } else if (chooser == "sanitation") {
                sanitationurl = chooserurl;
            } else if (chooser == "inspection") {
                inspectionurl = chooserurl;
            }

            Toast.makeText(getContext(), this.chooser + " pdf uploaded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error uploading document", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFirebaseData(String chooser, EditText txtCertification, TextView tvContext) {
        reference.orderByChild("buildingcode_certificate").equalTo(buildingcode + "_" + chooser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    Certifications certifications = snapshot.getValue(Certifications.class);

                    txtCertification.setText(certifications.getCertificateno());

                    if (certifications.getStatus() == 0) {
                        tvContext.setText("Uploaded");
                        tvContext.setVisibility(View.VISIBLE);
                    } else if (certifications.getStatus() == 1) {
                        tvContext.setText("Approved");
                        tvContext.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvContext.setText(null);
                    tvContext.setVisibility(View.GONE);
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

    private void uploadToFirebase(String chooser, EditText txtChooser, TextView tvContext, Uri selecteduri) {

        String certificateno = txtChooser.getText().toString().trim();

        if (!certificateno.isEmpty()) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Do you wish to proceed");
            builder.setMessage("Upload " + chooser + " data");
            builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ProgressDialog pd = new ProgressDialog(getContext());
                    final StorageReference ref = storageReference.child("BuildingCertifications/" + buildingcode + "/" + chooser + "_" + System.currentTimeMillis() + "." + getFileExtension(selecteduri));

                    pd.setTitle("Building Data");
                    pd.setMessage("Uploading " + chooser + " data....");

                    pd.show();

                    ref.putFile(selecteduri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Certifications certifications;
                                    if (uri == null) {
                                        certifications = new Certifications(
                                                buildingcode,
                                                buildingcode + "_" + chooser,
                                                certificateno,
                                                buildingcode + "_0",
                                                0
                                        );
                                    } else {
                                        certifications = new Certifications(
                                                buildingcode,
                                                buildingcode + "_" + chooser,
                                                certificateno,
                                                buildingcode + "_0",
                                                uri.toString(),
                                                0
                                        );
                                    }

                                    reference.push().setValue(certifications).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Uploaded " + chooser + " successfully", Toast.LENGTH_SHORT).show();
                                                tvContext.setVisibility(View.VISIBLE);
                                                tvContext.setText("Uploaded");
                                                pd.dismiss();
                                            } else {
                                                pd.dismiss();
                                                Toast.makeText(getContext(), "Error Sending " + chooser + " data", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            });
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            txtChooser.setError("Enter certificate no");
        }
    }

    private void checkComments(DatabaseReference reference, String buildingcode, String certificate, TextView tvFeedback) {
        reference.orderByChild("buildingcode_certificate").equalTo(buildingcode + "_" + certificate).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    tvFeedback.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "Does not exist", Toast.LENGTH_SHORT).show();
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

    private String getFileExtension(Uri uri) {
        ContentResolver cr = (getActivity()).getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

}