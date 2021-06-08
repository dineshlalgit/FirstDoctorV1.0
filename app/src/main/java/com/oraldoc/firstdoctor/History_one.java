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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.oraldoc.firstdoctor.pdf.fileinfomodel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class History_one extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference UsersRef;
    private StorageReference UserProfileImageRef;
    String currentUserID;
    AppCompatButton upload;
    TextView fileTitle;
    Uri filepath;
    private String strHyper, strMedication, strBleeding, strCardiac, strGastric, strSurgery, strAllergy, strAsthma, strJaundice, strDiabetic, strEpilepsy, strOtherCondition, strOtherConditionValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_one);
        loadingBar = new ProgressDialog(this);
        upload = findViewById(R.id.btnUpload);
        fileTitle = findViewById(R.id.fileTitle);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference();
        UsersRef = FirebaseDatabase.getInstance().getReference("mydocuments");
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bString = upload.getText().toString();
                if (bString.equals("Browse")) {
                    Dexter.withContext(getApplicationContext())
                            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    Intent intent = new Intent();
                                    intent.setType("application/pdf");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent, "Select Pdf Files"), 101);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                                }


                            }).check();
                    upload.setText("Upload");
                } else {
                    processupload(filepath);
                    upload.setText("Browse");

                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
    }

    public String onCheckboxClicked(View view) {
        // Is the view now checked?

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.chkbxHyper:
                if (checked)
                    strHyper = "Hyper Tension";
                else
                    strHyper = null;
                return strHyper;

            case R.id.chkbxerMedication:
                if (checked)
                    strMedication = "Under Medication";
                else
                    strMedication = null;

                return strMedication;

            case R.id.chkbxBleeding:
                if (checked)
                    strBleeding = "Bleeding Disorder";
                else
                    strBleeding = null;

                return strBleeding;

            case R.id.chkbxCardiac:
                if (checked)
                    strCardiac = "Cardiac Problems";
                else
                    strCardiac = null;
                return strCardiac;

            case R.id.chkbxGastric:
                if (checked)
                    strGastric = "Gastric Problems";
                else
                    strGastric = null;

                return strGastric;

            case R.id.chkbxSurgery:
                if (checked)
                    strSurgery = "Underwent Recent Surgery";
                else
                    strSurgery = null;

                return strSurgery;

            case R.id.chkbxAllergy:
                if (checked)
                    strAllergy = "Allergy";
                else
                    strAllergy = null;

                return strAllergy;

            case R.id.ckbxAsthma:
                if (checked)
                    strAsthma = "Asthma";
                else
                    strAsthma = null;

                return strAsthma;

            case R.id.chkbxJaundice:
                if (checked)
                    strJaundice = "Jaundice";
                else
                    strJaundice = null;

                return strJaundice;

            case R.id.chkbxDiabetic:
                if (checked)
                    strDiabetic = "Diabetic";
                else
                    strDiabetic = null;

                return strDiabetic;

            case R.id.chkbxEpilepsy:
                if (checked)
                    strEpilepsy = "Epilepsy";
                else
                    strEpilepsy = null;

                return strEpilepsy;
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            filepath = data.getData();

        }
    }

    public void onRadioButtonClicked(View view) {
        TextInputLayout tilother = (TextInputLayout) findViewById(R.id.tftilay_othercondition);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {

            case R.id.radio_no:
                if (checked) {
                    tilother.setVisibility(View.GONE);
                    strOtherCondition = "No";
                } else
                    strOtherCondition = null;

                break;
            case R.id.radio_yes:
                if (checked) {
                    strOtherCondition = "Yes";
                    tilother.setVisibility(View.VISIBLE);
                    tilother.requestFocus();
                } else
                    strOtherCondition = null;
                break;
        }
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
                            Intent LoginIntent = new Intent(History_one.this, HomePage.class);
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

    public void processupload(Uri filepath) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File Uploading....!!!");
        pd.show();

        final StorageReference reference = UserProfileImageRef.child("uploadPDF/" + System.currentTimeMillis() + ".pdf");
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                fileinfomodel obj = new fileinfomodel(fileTitle.getText().toString(), uri.toString());
                                UsersRef.child(UsersRef.push().getKey()).setValue(obj);

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();


                            }
                        });

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        pd.setMessage("Uploaded :" + (int) percent + "%");
                    }
                });
    }

    public void onNextButtonClick(View v) {

        TextInputEditText tiet_otherconditionValue;
        tiet_otherconditionValue = (TextInputEditText) findViewById(R.id.tiet_otherconditionValue);

        strOtherConditionValue = String.valueOf(tiet_otherconditionValue.getText());


        if (v.getId() == R.id.btnhistory) {

            String strCondition;

            strCondition = strHyper + (strMedication) + (strBleeding) + (strCardiac) + (strGastric) + (strSurgery) + (strAllergy) + (strAsthma) + (strJaundice) + (strDiabetic) + (strEpilepsy);

            if (strCondition.equals("nullnullnullnullnullnullnullnullnullnullnull")) {
                Toast.makeText(this, "Please select at least one condition that you are suffering from", Toast.LENGTH_LONG).show();
            } else if (strOtherCondition == null) {
                Toast.makeText(this, "Please select Yes/No from any other conditions", Toast.LENGTH_SHORT).show();
            } else if (strOtherCondition.equals("Yes") && (strOtherConditionValue.isEmpty())) {
                tiet_otherconditionValue.setError("Please enter pain increase reason");
                tiet_otherconditionValue.requestFocus();
            } else {
                loadingBar.setTitle("Saving Details");
                loadingBar.setMessage("Please wait, While we are saving your details.");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);
                StoreHistroy1Data();
            }
        }
    }

    private void StoreHistroy1Data() {

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child(currentDate);

        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("ComplainHyper", strHyper);
        userMap.put("ComplainMedication", strMedication);
        userMap.put("ComplainBleeding", strBleeding);
        userMap.put("ComplainCardiac", strCardiac);
        userMap.put("ComplainGastric", strGastric);
        userMap.put("ComplainSurgery", strSurgery);
        userMap.put("ComplainAllergy", strAllergy);
        userMap.put("ComplainAsthma", strAsthma);
        userMap.put("ComplainJaundice", strJaundice);
        userMap.put("ComplainDiabetic", strDiabetic);
        userMap.put("ComplainEpilepsy", strEpilepsy);
        userMap.put("ComplainPainIncreaseReason", strOtherConditionValue);

        UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadingBar.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder;

//                    Toast.makeText(Info.this, "Done", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(History_one.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(History_one.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Confirmation")
                            .setIcon(R.drawable.ic_info)
                            .setMessage("Basic History_one Details Saved. \nPlease fill other forms")
                            .setPositiveButton("Ok, Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    dialog.dismiss();
                                    Intent LoginIntent = new Intent(History_one.this, History_two.class);
                                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(LoginIntent);
                                }
                            })
                            .show();
                } else {
                    String mgs = task.getException().getMessage();
                    loadingBar.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder;

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(History_one.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(History_one.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Error")
                            .setIcon(R.drawable.ic_info)
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
}