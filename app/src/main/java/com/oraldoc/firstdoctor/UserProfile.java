package com.oraldoc.firstdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class UserProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private String currentUserID,strtvUserName,strtvUserAge,strtvUserEmail,strtvUserMobile;
    private TextInputEditText tvUserName,tvUserAge,tvUserEmail,tvUserMobile;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        tvUserName = (TextInputEditText)findViewById(R.id.tietname);
        tvUserAge = (TextInputEditText)findViewById(R.id.tietage);
        tvUserEmail = (TextInputEditText)findViewById(R.id.tietEmail);
        tvUserMobile = (TextInputEditText)findViewById(R.id.tietmobile);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    //Personal Details
                    strtvUserName = snapshot.child("userName").getValue().toString();
                    strtvUserAge = snapshot.child("userAge").getValue().toString();
                    strtvUserEmail = snapshot.child("userEmail").getValue().toString();
                    strtvUserMobile = snapshot.child("userMobile").getValue().toString();
//
                    tvUserName.setText(strtvUserName);
                    tvUserAge.setText(strtvUserAge);
                    tvUserEmail.setText(strtvUserEmail);
                    tvUserMobile.setText(strtvUserMobile);
                }
                else{
                    Toast.makeText(UserProfile.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onNextButtonClick(View v){


        tvUserName = (TextInputEditText)findViewById(R.id.tietname);
        tvUserAge = (TextInputEditText)findViewById(R.id.tietage);
        tvUserEmail = (TextInputEditText)findViewById(R.id.tietEmail);
        tvUserMobile = (TextInputEditText)findViewById(R.id.tietmobile);

        strtvUserName = tvUserName.getText().toString();
        strtvUserAge = tvUserAge.getText().toString();
        strtvUserEmail = tvUserEmail.getText().toString();
        strtvUserMobile = tvUserMobile.getText().toString();

        if(v.getId ()==R.id.btn_submit){

            if (strtvUserName.isEmpty() || strtvUserName.length()<3 || (!strtvUserName.matches("[A-Za-z0-9 ]*"))){
                tvUserName.setError("Mandatory field / Invalid Input");
                tvUserName.requestFocus();
            }
            else if (strtvUserAge.isEmpty() || strtvUserAge.length()>2 || (!strtvUserAge.matches("[A-Za-z0-9]*"))){
                tvUserAge.setError("Mandatory field / Invalid Input");
                tvUserAge.requestFocus();
            }
            else if (strtvUserEmail.isEmpty()){
                tvUserEmail.setError("Mandatory field / Invalid Input ");
                tvUserEmail.requestFocus();
            }
            else if (strtvUserMobile.isEmpty() || strtvUserMobile.length()<10 || (!strtvUserMobile.matches("[0-9]*"))){
                tvUserMobile.setError("Mandatory field / Invalid Input");
                tvUserMobile.requestFocus();
            }
            else{
                loadingBar.setTitle("Saving Details");
                loadingBar.setMessage("Please wait, While we are saving your details.");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(false);

                StoreInfoData();
            }
        }
        else if(v.getId ()==R.id.btn_edit){
            Button btnedit = (Button)findViewById(R.id.btn_edit);
            Button btn_submit = (Button)findViewById(R.id.btn_submit);
            btn_submit.setVisibility(View.VISIBLE);
            btnedit.setVisibility(View.INVISIBLE);
            tvUserName.setEnabled(true);
            tvUserAge.setEnabled(true);
            tvUserEmail.setEnabled(true);
            tvUserMobile.setEnabled(true);
        }
    }

    private void StoreInfoData() {

        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("userName", strtvUserName);
        userMap.put("userAge", strtvUserAge);
        userMap.put("userEmail", strtvUserEmail);
        userMap.put("userMobile", strtvUserMobile);

        UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    loadingBar.dismiss();
                    Button btnedit = (Button)findViewById(R.id.btn_edit);
                    Button btn_submit = (Button)findViewById(R.id.btn_submit);

                    btn_submit.setVisibility(View.INVISIBLE);
                    btnedit.setVisibility(View.VISIBLE);

                    tvUserName.setEnabled(false);
                    tvUserAge.setEnabled(false);
                    tvUserEmail.setEnabled(false);
                    tvUserMobile.setEnabled(false);
                    androidx.appcompat.app.AlertDialog.Builder builder;

//                    Toast.makeText(Info.this, "Done", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(UserProfile.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(UserProfile.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Confirmation")
                            .setIcon(R.drawable.ic_info)
                            .setMessage("Profile Updated!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    dialog.dismiss();
//                                    Intent LoginIntent = new Intent(UserProfile.this, Complain.class);
//                                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(LoginIntent);
                                }
                            })
                            .show();
                }
                else{
                    String mgs = task.getException().getMessage();
                    loadingBar.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder;

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(UserProfile.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(UserProfile.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
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