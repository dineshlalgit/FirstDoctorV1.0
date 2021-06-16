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
import android.widget.RadioButton;
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
import com.google.firebase.storage.StorageReference;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Info extends AppCompatActivity {
    private TextInputEditText tietName,tietAge,tietAddress,tietCity,tietZip,tietEmail,tietMobile;
    private String stringName,stringAge,stringAddress,stringCity,stringZip,stringEmail,stringMobile,stringGndr,stringChkbox,stringChkboxCon,stringChkboxckd,stringComplianfor;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference UsersRef;
    private StorageReference UserProfileImageRef;
    String currentUserID;
    NoInternetDialog noInternetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
    }

    public String onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    stringGndr = "male";
                return stringGndr;
            case R.id.radio_female:
                if (checked)
                    stringGndr = "female";
                return stringGndr;

            case R.id.radio_other:
                if (checked)
                    stringGndr = "other";
                return stringGndr;

            case R.id.radio_self:
                if (checked)
                    ComplainSelfData();
                stringComplianfor = "For-self-";
                return stringComplianfor;

            case R.id.radio_complainother:
                if (checked)
                    ComplainOtherData();
                stringComplianfor = "For-other-";
                return stringComplianfor;
        }
        return null;
    }

    public String onCheckboxClicked(View view) {
        // Is the view now checked?

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBoxAgreeInfo:
                if (checked)
                    stringChkbox = "Agreed";
                else
                    stringChkbox = null;

                return stringChkbox;

            case R.id.checkBoxConsent:
                if (checked)
                    stringChkboxCon = "AgreedCon";
                else
                    stringChkboxCon = null;
                return stringChkboxCon;
        }
        return null;
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
                            Intent LoginIntent = new Intent(Info.this, HomePage.class);
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

    public void onNextButtonClick(View v){

        tietName = (TextInputEditText) findViewById(R.id.tietname);
        tietAge = (TextInputEditText) findViewById(R.id.tietage);
        tietAddress = (TextInputEditText) findViewById(R.id.tietaddress);
        tietCity = (TextInputEditText) findViewById(R.id.tietcity);
        tietZip = (TextInputEditText) findViewById(R.id.tietzip);
        tietMobile = (TextInputEditText) findViewById(R.id.tietmobile);

        stringName = tietName.getText().toString();
        stringAge = tietAge.getText().toString();
        stringAddress = tietAddress.getText().toString();
        stringCity = tietCity.getText().toString();
        stringZip = tietZip.getText().toString();
        stringMobile = tietMobile.getText().toString();

        if(v.getId ()==R.id.btnnxtinfo){

            stringChkboxckd = stringChkbox+(stringChkboxCon);

            if (stringComplianfor == null){
                Toast.makeText(this, "Please select for whom you are complaining for!", Toast.LENGTH_SHORT).show();
            }
            else if (stringName.isEmpty() || stringName.length()<3 || (!stringName.matches("[A-Za-z0-9 ]*"))){
                tietName.setError("Mandatory field / Invalid Input");
                tietName.requestFocus();
            }
            else if (stringAge.isEmpty() || stringAge.length()>2 || (!stringAge.matches("[A-Za-z0-9]*"))){
                tietAge.setError("Mandatory field / Invalid Input");
                tietAge.requestFocus();
            }
            else if (stringGndr == null){
                Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            }
            else if (stringAddress.isEmpty() || stringAddress.length()<10 || (!stringAddress.matches("[A-Za-z0-9 ,]*"))){
                tietAddress.setError("Mandatory field / Invalid Input ");
                tietAddress.requestFocus();
            }
            else if (stringCity.isEmpty() || stringCity.length()<4 || (!stringCity.matches("[A-Za-z0-9 ]*"))){
                tietCity.setError("Mandatory field / Invalid Input");
                tietCity.requestFocus();
            }
            else if (stringZip.isEmpty() || stringZip.length()<6 || (!stringZip.matches("[0-9]*"))){
                tietZip.setError("Mandatory field / Invalid Input");
                tietZip.requestFocus();
            }
            else if (stringMobile.isEmpty() || stringMobile.length()<10  || (!stringMobile.matches("[0-9]*"))){
                tietMobile.setError("Mandatory field / Invalid Input");
                tietMobile.requestFocus();
            }
            else if (!stringChkboxckd.equals("AgreedAgreedCon")) {
                Toast.makeText(this, "Please agree both condition to Continue", Toast.LENGTH_SHORT).show();
            }
            else{
                loadingBar.setTitle("Saving Details");
                loadingBar.setMessage("Please wait, While we are saving your details.");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(false);
                StoreInfoData();
            }
        }
        else if(v.getId() == R.id.btnclrform){
            ComplainOtherData();
        }
    }

    private void StoreInfoData() {

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());

        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child(stringComplianfor+(currentDate));

        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("ComplainName", stringName);
        userMap.put("ComplainAge", stringAge);
        userMap.put("ComplainSex", stringGndr);
        userMap.put("ComplainAddress", stringAddress);
        userMap.put("ComplainCity", stringCity);
        userMap.put("ComplainZip", stringZip);
        userMap.put("ComplainMobile", stringMobile);
        userMap.put("ComplainFor", stringComplianfor);

        UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    loadingBar.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder;

//                    Toast.makeText(Info.this, "Done", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Info.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Info.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Confirmation")
                            .setIcon(R.drawable.ic_info)
                            .setMessage("Basic Details Saved. \nPlease fill other forms")
                            .setPositiveButton("Ok, Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    dialog.dismiss();
                                    Intent LoginIntent = new Intent(Info.this, Complain.class);
                                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    LoginIntent.putExtra("Complain",stringComplianfor);
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
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Info.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Info.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
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

    private void ComplainSelfData(){
        mAuth = FirebaseAuth.getInstance();
        String currentUserID;
        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Getting Info");
        loadingBar.setMessage("Please wait, While we are fetching your details.");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);

        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        tietName = (TextInputEditText)findViewById(R.id.tietname);
        tietAge = (TextInputEditText)findViewById(R.id.tietage);
        tietEmail = (TextInputEditText)findViewById(R.id.tietEmail);
        tietMobile = (TextInputEditText)findViewById(R.id.tietmobile);
        RadioButton radiomale = (RadioButton)findViewById(R.id.radio_male);
        RadioButton radiofemale = (RadioButton)findViewById(R.id.radio_female);
        RadioButton radioother = (RadioButton)findViewById(R.id.radio_other);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String currentUserID,strtvUserName,strtvUserAge,strtvUserEmail,strtvUserMobile,strvUserGender;

                    //Personal Details
                    strtvUserName = snapshot.child("userName").getValue().toString();
                    strtvUserAge = snapshot.child("userAge").getValue().toString();
                    strtvUserEmail = snapshot.child("userEmail").getValue().toString();
                    strtvUserMobile = snapshot.child("userMobile").getValue().toString();
                    strvUserGender = snapshot.child("userSex").getValue().toString();
//
                    tietName.setText(strtvUserName);
                    tietAge.setText(strtvUserAge);
                    tietEmail.setText(strtvUserEmail);
                    tietMobile.setText(strtvUserMobile);

                    switch (strvUserGender) {
                        case "male":
                            radiomale.setChecked(true);;
                            radiofemale.setChecked(false);
                            radioother.setChecked(false);
                            break;
                        case "female":
                            radiomale.setChecked(false);
                            radiofemale.setChecked(true);
                            radioother.setChecked(false);
                            break;
                        case "other":
                            radiomale.setChecked(false);
                            radiofemale.setChecked(false);
                            radioother.setChecked(true);
                            break;
                    }

                    loadingBar.dismiss();
                }
                else{
                    Toast.makeText(Info.this, "Error updating", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ComplainOtherData(){
        tietName = (TextInputEditText)findViewById(R.id.tietname);
        tietAge = (TextInputEditText)findViewById(R.id.tietage);
        tietEmail = (TextInputEditText)findViewById(R.id.tietEmail);
        tietMobile = (TextInputEditText)findViewById(R.id.tietmobile);
        tietAddress = (TextInputEditText)findViewById(R.id.tietaddress);
        tietCity = (TextInputEditText)findViewById(R.id.tietcity);
        tietZip = (TextInputEditText)findViewById(R.id.tietzip);
        RadioButton radiomale = (RadioButton)findViewById(R.id.radio_male);
        RadioButton radiofemale = (RadioButton)findViewById(R.id.radio_female);
        RadioButton radioother = (RadioButton)findViewById(R.id.radio_other);

        tietName.setText(null);
        tietAge.setText(null);
        tietEmail.setText(null);
        tietMobile.setText(null);
        tietAddress.setText(null);
        tietCity.setText(null);
        tietZip.setText(null);

        radiomale.setChecked(false);
        radiofemale.setChecked(false);
        radioother.setChecked(false);
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