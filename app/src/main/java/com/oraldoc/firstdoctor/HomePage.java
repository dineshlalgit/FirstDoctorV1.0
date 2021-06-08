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
        } else if (v.getId() == R.id.btnconult) {
            Toast.makeText(HomePage.this, "Button in progress", Toast.LENGTH_SHORT).show();
        }
    }

}