package com.oraldoc.firstdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;

import java.util.HashMap;
import java.util.Objects;

public class UserProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private String currentUserID,strtvUserName,strtvUserAge,strtvUserEmail,strtvUserMobile;
    private TextInputEditText tvUserName,tvUserAge,tvUserEmail,tvUserMobile;
    private ProgressDialog loadingBar;
    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Getting Info");
        loadingBar.setMessage("Please wait, While we are fetching your details.");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

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
                    loadingBar.dismiss();
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
            else if (strtvUserEmail.isEmpty() || strtvUserEmail.length() < 5 || (!android.util.Patterns.EMAIL_ADDRESS.matcher(strtvUserEmail).matches())){
                tvUserEmail.setError("Mandatory field / Invalid Input ");
                tvUserEmail.requestFocus();
            }
            else if (strtvUserMobile.isEmpty() || strtvUserMobile.length()<10 || (!strtvUserMobile.matches("[0-9]*"))){
                tvUserMobile.setError("Mandatory field / Invalid Input");
                tvUserMobile.requestFocus();
            }
            else{

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
        else if(v.getId ()==R.id.btn_chanagepass){
            ChangePassword();
        }
    }

    private void ChangePassword() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Password Reset");
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText passet= new EditText(this);


        // write the email using which you registered
        passet.setHint("Please enter your new password");
        passet.setMinEms(16);
        passet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(passet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

//        linearLayout.setPadding(10,10,10,10);
//        builder.setView(linearLayout);

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String newPassword = passet.getText().toString();
                if(newPassword.isEmpty()){
                    passet.setError("Please enter valid password in between 8 to 16 Alphanumeric");
                }
                else{
                    dialog.dismiss();
                    loadingBar.setTitle("Saving Password");
                    loadingBar.setMessage("Please wait, While we are saving your New Password.");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(false);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        LoginAgain();
                                    }
                                    else{
                                        loadingBar.dismiss();
                                        Toast.makeText(UserProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void LoginAgain(){
        loadingBar.dismiss();
        androidx.appcompat.app.AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            builder = new androidx.appcompat.app.AlertDialog.Builder(UserProfile.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new androidx.appcompat.app.AlertDialog.Builder(UserProfile.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        }
        builder.setTitle("Successful")
                .setIcon(R.drawable.ic_info)
                .setMessage("Password Updated \n Please login to continue")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        mAuth.signOut();
                        Intent logout = new Intent(UserProfile.this,MainActivity.class);
                        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                    }
                })
                .show();
    }

    private void StoreInfoData(){

        loadingBar.setTitle("Saving Details");
        loadingBar.setMessage("Please wait, While we are saving your details.");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Objects.requireNonNull(user).updateEmail(Objects.requireNonNull(tvUserEmail.getText()).toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

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
                                        Button btnedit = (Button)findViewById(R.id.btn_edit);
                                        Button btn_submit = (Button)findViewById(R.id.btn_submit);

                                        btn_submit.setVisibility(View.INVISIBLE);
                                        btnedit.setVisibility(View.VISIBLE);

                                        tvUserName.setEnabled(false);
                                        tvUserAge.setEnabled(false);
                                        tvUserEmail.setEnabled(false);
                                        tvUserMobile.setEnabled(false);
                                        loadingBar.dismiss();
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
//    private void ChangeEmail(){
//
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setTitle("Provide Current Credentials");
//        LinearLayout linearLayout=new LinearLayout(this);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        final EditText emailet= new EditText(this);
//        final EditText passet= new EditText(this);
//
//        // write the email using which you registered
//        emailet.setHint("Enter your registered email address");
//        emailet.setMinEms(16);
//        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//
//        passet.setHint("Enter your password");
//        passet.setMinEms(16);
//        passet.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        linearLayout.addView(emailet);
//        linearLayout.addView(passet);
//        linearLayout.setPadding(10,10,10,10);
//        builder.setView(linearLayout);
//
////        linearLayout.setPadding(10,10,10,10);
////        builder.setView(linearLayout);
//
//        // Click on Recover and a email will be sent to your registered email id
//        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                String emaill=emailet.getText().toString().trim();
//                String passl=passet.getText().toString().trim();
//
//                if (emaill.isEmpty()){
//                    emailet.setError("Enter Email Id");
//                    emailet.requestFocus();
//                }
//                else if (passl.isEmpty()){
//                    passet.setError("Enter Email Id");
//                    passet.requestFocus();
//                }
//                else{
//                    dialog.dismiss();
//                    loadingBar.setTitle("Saving Details");
//                    loadingBar.setMessage("Please wait, While we are saving your details.");
//                    loadingBar.show();
//                    loadingBar.setCanceledOnTouchOutside(false);
//
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                    // Get auth credentials from the user for re-authentication
//                    AuthCredential credential = EmailAuthProvider.getCredential(emaill, passl); // Current Login Credentials
//
//                    // Prompt the user to re-provide their sign-in credentials
//                    Objects.requireNonNull(user).reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            Log.d("value", "User re-authenticated.");
//
//                            // Now change your email address \\
//                            //----------------Code for Changing Email Address----------\\
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            user.updateEmail(tvUserEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        StoreInfoData();
//                                        loadingBar.dismiss();
//                                        Toast.makeText(UserProfile.this, "Email Changed" + " Current Email is " + tvUserEmail.getText().toString(), Toast.LENGTH_LONG).show();
//                                    }
//                                    else{
//                                        String mgs = Objects.requireNonNull(task.getException()).getMessage();
//                                        loadingBar.dismiss();
//                                        Toast.makeText(UserProfile.this, "mgs", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }
//                    });
//                }
////                Boolean wantToCloseDialog = false;
////                //Do stuff, possibly set wantToCloseDialog to true then...
////                if(wantToCloseDialog)
////                    dialog.dismiss();
//                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
//            }
//        });
//    }
    @Override
    protected void onResume() {
        super.onResume();
//            NoInternetDialog noInternetDialog;

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