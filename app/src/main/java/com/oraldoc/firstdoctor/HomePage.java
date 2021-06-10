package com.oraldoc.firstdoctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private String currentUserID, strtvUserName;
    private TextView tvUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();
//
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        tvUserName = (TextView) findViewById(R.id.tvUserName);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    //Personal Details
                    strtvUserName = snapshot.child("userName").getValue().toString();
//
                    tvUserName.setText("Hi! " + strtvUserName);
                } else {

                    Toast.makeText(HomePage.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    //For Back press and confirmation for exit
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builderexit;
        builderexit = new AlertDialog.Builder(this);
        builderexit.setIcon(R.mipmap.ic_launcher);
        builderexit.setTitle("Exit")
                .setMessage("Do you really want to exit?")
                .setPositiveButton("Yes,Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
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

    public void onNextButtonClick(View v) {
        if (v.getId() == R.id.btnfillform) {
            Intent nxt = new Intent(HomePage.this, Info.class);
            startActivity(nxt);
        }
        else if (v.getId() == R.id.tvUserName) {
            GotoUserProfile();
        }
        else if (v.getId() == R.id.tvProfile) {
            GotoUserProfile();
        }
        else if (v.getId() == R.id.tvLogout) {
            LogOut();
        }
        else if (v.getId() == R.id.btnconult) {
            Toast.makeText(HomePage.this, "Coming Soon..... \n With many other Features", Toast.LENGTH_SHORT).show();
        }
    }

    private void GotoUserProfile(){
        Intent userprofile = new Intent(HomePage.this, UserProfile.class);
        startActivity(userprofile);
    }
    private void LogOut(){
        androidx.appcompat.app.AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            builder = new androidx.appcompat.app.AlertDialog.Builder(HomePage.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new androidx.appcompat.app.AlertDialog.Builder(HomePage.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        }
        builder.setTitle("Alert")
                .setIcon(R.drawable.ic_info)
                .setMessage("Do you really want to logout")
                .setCancelable(false)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        mAuth.signOut();
                        Toast.makeText(HomePage.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();                        Intent logout = new Intent(HomePage.this,MainActivity.class);
                        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                       dialog.dismiss();
                    }
                })
                .show();
    }


}