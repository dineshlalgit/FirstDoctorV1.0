package com.oraldoc.firstdoctor;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Examination_one extends AppCompatActivity {
    private FirebaseAuth mAuth;


    private ProgressDialog loadingBar;
    private DatabaseReference UsersRef;
    private StorageReference UserProfileImageRef;
    String currentUserID;
    AppCompatButton upload_b;
    private String strLips, strTongue, strMouthfloor, strCheeks, strMouthroof, strGums, strChecked;
    ProgressDialog progressDialog;
    TextView editText;
    private static final int PICK_IMAGE = 1;
    ArrayList<Uri> imageList = new ArrayList<>();
    private Uri imageUri;
    private int upload_count = 0;
    NoInternetDialog noInternetDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_one);
        loadingBar = new ProgressDialog(this);
        upload_b = findViewById(R.id.btnUpload);
        editText = findViewById(R.id.alert);
        progressDialog = new ProgressDialog(Examination_one.this);
        progressDialog.setMessage("Image Uploading Please Wait....");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        upload_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                uploadtofirebase();
                String bString = upload_b.getText().toString();
                if (bString.equals("Browse")) {
                    Dexter.withActivity(Examination_one.this)
                            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                    startActivityForResult(Intent.createChooser(intent, "Please select Image"), PICK_IMAGE);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    upload_b.setText("Browse");
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();

                    upload_b.setText("Upload");
                } else {
                    uploadtofirebase();
                    upload_b.setText("Browse");
                }

            }
        });

    }

    public String onCheckboxClicked(View view) {
        // Is the view now checked?

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.chkbxLips:
                if (checked)
                    strLips = "Lips";
                else
                    strLips = null;
                return strLips;

            case R.id.ckbxTongue:
                if (checked)
                    strTongue = "Tongue";
                else
                    strTongue = null;

                return strTongue;

            case R.id.chkbxMouthfloor:
                if (checked)
                    strMouthfloor = "Floor of mouth";
                else
                    strMouthfloor = null;

                return strMouthfloor;

            case R.id.chkbxCheeks:
                if (checked)
                    strCheeks = "Cheeks";
                else
                    strCheeks = null;
                return strCheeks;

            case R.id.chkbxMouthroof:
                if (checked)
                    strMouthroof = "Roof of mouth";
                else
                    strMouthroof = null;

                return strMouthroof;

            case R.id.chkbxGums:
                if (checked)
                    strGums = "Gums";
                else
                    strGums = null;

                return strGums;
        }
        return null;
    }

    public void onNextButtonClick(View v) {
        strChecked = strLips + (strTongue) + (strMouthfloor) + (strCheeks) + (strMouthroof) + (strGums);

        if (v.getId() == R.id.btnhistory) {
            if (strChecked.equals("nullnullnullnullnullnull")) {
                Toast.makeText(this, "Please select one place to view", Toast.LENGTH_LONG).show();
            } else {
                loadingBar.setTitle("Saving Details");
                loadingBar.setMessage("Please wait, While we are saving your details.");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(false);
                StoreExaminationData();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
//            filepath = data.getData();
//
            if (data.getClipData() != null) {
                int countClipData = data.getClipData().getItemCount();
                int currentImageSelect = 0;
                while (currentImageSelect < countClipData) {
                    imageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                    imageList.add(imageUri);
                    currentImageSelect = currentImageSelect + 1;
                }
                editText.setVisibility(View.INVISIBLE);
                editText.setText("You have selected" + imageList.size() + "Images");
            } else {

                Toast.makeText(this, "Please Select Multiple Image", Toast.LENGTH_SHORT).show();
                upload_b.setText("Browse");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadtofirebase() {

        progressDialog.show();
        StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        for (upload_count = 0; upload_count < imageList.size(); upload_count++) {
            Uri individualImg = imageList.get(upload_count);
            StorageReference reference = imageFolder.child("Image" + individualImg.getLastPathSegment());
            reference.putFile(individualImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);
                            storeLink(url);
                        }
                    });
                }
            });
        }

    }

    private void storeLink(String url) {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("OralImage").child(currentUserID).child(currentDate);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("imgLink", url);
        databaseReference.push().setValue(hashMap);
        progressDialog.dismiss();
        editText.setVisibility(View.VISIBLE);
        editText.setText("Image Uploaded Successfully");

    }

    public void onBackPressed() {
        AlertDialog.Builder builderexit;
        builderexit = new AlertDialog.Builder(this);
        builderexit.setIcon(R.mipmap.ic_launcher);
        builderexit.setTitle("Exit")
                .setMessage("Do you really want go back \nData will not be saved")
                .setPositiveButton("Yes,Go back to Homepage", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            dialog.dismiss();
                            Intent LoginIntent = new Intent(Examination_one.this, HomePage.class);
                            LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(LoginIntent);
                        } else {
                            finish();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void StoreExaminationData() {

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());

        Intent intent = getIntent();
        String ComplainFor = intent.getStringExtra("Complain");

        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child(ComplainFor+(currentDate));

        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("ComplainLips", strLips);
        userMap.put("ComplainTongue", strTongue);
        userMap.put("ComplainMouthfloor", strMouthfloor);
        userMap.put("ComplainCheeks", strCheeks);
        userMap.put("ComplainMouthroof", strMouthroof);
        userMap.put("ComplainGums", strGums);

        UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
//                    loadingBar.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder;

//                    Toast.makeText(Info.this, "Done", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Examination_one.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Examination_one.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Confirmation")
//                .setIcon(R.drawable.ic_info)
                            .setMessage("Successfully Uploaded Details, we will get back to you soon")
                            .setPositiveButton("Ok, Visit Home", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    dialog.dismiss();
                                    Intent LoginIntent = new Intent(Examination_one.this, HomePage.class);
                                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(LoginIntent);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    String mgs = task.getException().getMessage();
                    loadingBar.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder;

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Examination_one.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Examination_one.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Error")
                            .setIcon(R.drawable.ic_info)
                            .setCancelable(false)
                            .setMessage("Unsuccessful. \n Please try again later.\n" + mgs)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        NoInternetDialog noInternetDialog;

        NoInternetDialog.Builder builder1 = new NoInternetDialog.Builder(this);

        builder1.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
            }
        });
        builder1.setCancelable(false); // Optional
        builder1.setNoInternetConnectionTitle("No Internet"); // Optional
        builder1.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        builder1.setShowInternetOnButtons(true); // Optional
        builder1.setPleaseTurnOnText("Please turn on"); // Optional
        builder1.setWifiOnButtonText("Wifi"); // Optional
        builder1.setMobileDataOnButtonText("Mobile data"); // Optional

        builder1.setOnAirplaneModeTitle("No Internet"); // Optional
        builder1.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        builder1.setPleaseTurnOffText("Please turn off"); // Optional
        builder1.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        builder1.setShowAirplaneModeOffButtons(true); // Optional

        noInternetDialog = builder1.build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (noInternetDialog != null) {
            noInternetDialog.destroy();
        }
    }
}