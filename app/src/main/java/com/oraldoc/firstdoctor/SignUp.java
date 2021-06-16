package com.oraldoc.firstdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;

import java.util.HashMap;
import java.util.Objects;

public class SignUp extends AppCompatActivity {
    private TextInputEditText tietName,tietAge,tietEmail,tietMobile,tietPassword,tietPasswordConfirm;
    private String stringName,stringAge,stringEmail,stringMobile,stringGndr,stringPassword,stringPasswordConfirm;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    NoInternetDialog noInternetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
//        currentUserID = mAuth.getCurrentUser().getUid();
//        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

    }

    private void SendUserToSignInActivity() {
        Intent LoginIntent = new Intent(this, MainActivity.class);
        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(LoginIntent);
        finish();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    stringGndr = "male";
                break;

            case R.id.radio_female:
                if (checked)
                    stringGndr = "female";
                break;

            case R.id.radio_other:
                if (checked)
                    stringGndr = "other";
                break;
        }
    }

    public void onNextButtonClick(View v){

        tietName = (TextInputEditText) findViewById(R.id.tietname);
        tietAge = (TextInputEditText) findViewById(R.id.tietage);
        tietEmail = (TextInputEditText) findViewById(R.id.tietEmail);
        tietMobile = (TextInputEditText) findViewById(R.id.tietmobile);
        tietPassword = (TextInputEditText) findViewById(R.id.tietPassword);
        tietPasswordConfirm = (TextInputEditText) findViewById(R.id.tietPasswordConfirm);

        stringName = tietName.getText().toString();
        stringAge = tietAge.getText().toString();
        stringEmail = tietEmail.getText().toString();
        stringMobile = tietMobile.getText().toString();
        stringPassword = tietPassword.getText().toString();
        stringPasswordConfirm = tietPasswordConfirm.getText().toString();

        if(v.getId ()==R.id.btn_signup){

            if (stringName.isEmpty() || stringName.length()<3 || (!stringName.matches("[A-Za-z ]*"))){
                tietName.setError("Mandatory field / Invalid Input");
                tietName.requestFocus();
            }
            else if (stringAge.isEmpty() || stringAge.length()>2 || (!stringAge.matches("[0-9]*"))){
                tietAge.setError("Mandatory field / Invalid Input");
                tietAge.requestFocus();
            }
            else if (stringGndr == null){
                Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            }
            else if (stringEmail.isEmpty() || stringEmail.length() < 5 || (!android.util.Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches())){
                tietEmail.setError("Mandatory field / Invalid Input ");
                tietEmail.requestFocus();
            }
            else if (stringMobile.isEmpty() || stringMobile.length()<10  || (!stringMobile.matches("[0-9]*"))){
                tietMobile.setError("Mandatory field / Invalid Input");
                tietMobile.requestFocus();
            }
            else if (stringPassword.isEmpty() || stringPassword.length() < 8 || (!stringPassword.matches("[A-Za-z0-9@#$%_]*"))) {
                tietPassword.setError("Mandatory field / Invalid Input");
                tietPassword.requestFocus();

            } else if (stringPasswordConfirm.isEmpty() || !stringPasswordConfirm.contentEquals(stringPassword)) {
                tietPasswordConfirm.setError("Mandatory field / Invalid Input / Unmatched Password");
                tietPasswordConfirm.requestFocus();

            }
            else {

                loadingBar.setTitle("Creating New Account");
                loadingBar.setMessage("Please wait, While we are creating your new account.");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(false);


                mAuth.createUserWithEmailAndPassword(stringEmail,stringPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    StoreOtherData();
//                                    loadingBar.dismiss();
//                                    androidx.appcompat.app.AlertDialog.Builder builder;
//
//                                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
//                                        builder = new androidx.appcompat.app.AlertDialog.Builder(SignUp.this, android.R.style.Theme_Material_Dialog_Alert);
//                                    } else {
//                                        builder = new androidx.appcompat.app.AlertDialog.Builder(SignUp.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
//                                    }
//                                    builder.setTitle("Confirmation")
//                                            .setIcon(R.drawable.ic_info)
//                                            .setMessage("Registration Successful. \nPlease Login to continue")
//                                            .setPositiveButton("Ok, Continue", new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    // do nothing
//                                                    dialog.dismiss();
//                                                    Intent LoginIntent = new Intent(SignUp.this, MainActivity.class);
//                                                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                    startActivity(LoginIntent);
//                                                }
//                                            })
//                                            .show();
                                }
                                else{
                                    String mgs = task.getException().getMessage();
                                    loadingBar.dismiss();
                                    androidx.appcompat.app.AlertDialog.Builder builder;

                                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                                        builder = new androidx.appcompat.app.AlertDialog.Builder(SignUp.this, android.R.style.Theme_Material_Dialog_Alert);
                                    } else {
                                        builder = new androidx.appcompat.app.AlertDialog.Builder(SignUp.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                                    }
                                    builder.setTitle("Confirmation")
                                            .setIcon(R.drawable.ic_info)
                                            .setMessage("Registration Unsuccessful. \n Please try again later." + mgs)
                                            .setPositiveButton("Ok, Continue", new DialogInterface.OnClickListener() {
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
        else if(v.getId() == R.id.tvSignin){
            SendUserToSignInActivity();
        }

    }

    private void StoreOtherData() {
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference UsersRef;
        String currentUserID;

        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("userName", stringName);
        userMap.put("userAge", stringAge);
        userMap.put("userSex", stringGndr);
        userMap.put("userEmail", stringEmail);
        userMap.put("userMobile", stringMobile);

        UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    loadingBar.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder;

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(SignUp.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(SignUp.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Confirmation")
                            .setIcon(R.drawable.ic_info)
                            .setMessage("Registration Successful. \nPlease Login to continue")
                            .setPositiveButton("Ok, Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    dialog.dismiss();
                                    Intent LoginIntent = new Intent(SignUp.this, MainActivity.class);
                                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(LoginIntent);
                                }
                            })
                            .show();
                }
                else{
                    String mgs = task.getException().getMessage();
                    loadingBar.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder;

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(SignUp.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(SignUp.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Confirmation")
                            .setIcon(R.drawable.ic_info)
                            .setMessage("Registration Unsuccessful. \n Please try again later." + mgs)
                            .setPositiveButton("Ok, Continue", new DialogInterface.OnClickListener() {
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