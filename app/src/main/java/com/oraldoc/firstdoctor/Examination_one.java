package com.oraldoc.firstdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Examination_one extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference UsersRef;
    private StorageReference UserProfileImageRef;
    String currentUserID;
    private String strLips,strTongue,strMouthfloor,strCheeks,strMouthroof,strGums,strChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_one);
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
    }

    public String onCheckboxClicked(View view) {
        // Is the view now checked?

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
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

    public void onNextButtonClick(View v){
        strChecked = strLips+(strTongue)+(strMouthfloor)+(strCheeks)+(strMouthroof)+(strGums);

        if(v.getId ()==R.id.btnhistory){
            if(strChecked.equals("nullnullnullnullnullnull")){
                Toast.makeText(this, "Please select one place to view", Toast.LENGTH_LONG).show();
            }
            else{
                loadingBar.setTitle("Saving Details");
                loadingBar.setMessage("Please wait, While we are saving your details.");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(false);
                StoreExaminationData();
            }
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
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child(currentDate);

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
                if (task.isSuccessful()){
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
                }
                else{
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