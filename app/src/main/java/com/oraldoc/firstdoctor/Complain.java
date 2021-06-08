package com.oraldoc.firstdoctor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Complain extends AppCompatActivity {
    private EditText etPrblm;
    private CheckBox chkbxBleedingGums,chkbxInability,chkbxDiscolored,chkbxTeeth,chxbxSwelling,chxbxGrowth,chxbxPain,chkbxrightjaw,chkbxleftjaw,chkbxupperjaw,chkbxlowerjaw;
    private TextInputEditText tilotherPainIncrease,tilotherPainDecrease,tietdays;
    private String
            strchkbxBleedingGums,strchkbxInability,strchkbxDiscolored,strchkbxTeeth,strchxbxSwelling, strchxbxGrowth,strchxbxPain,strchkbxrightjaw,strchkbxleftjaw,strchkbxupperjaw,strchkbxlowerjaw,
            strtilotherPainIncrease,strtilotherPainDecrease,stretdays,
            strSufferingTime,strPainType,strPainType2,strPainIncrease,strPainDecrease;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference UsersRef;
    private StorageReference UserProfileImageRef;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

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
            case R.id.chkbxBleeding:
                if (checked)
                    strchkbxBleedingGums = "Bleeding Gums";
                else
                    strchkbxBleedingGums = null;
                return strchkbxBleedingGums;

            case R.id.chkbxInability:
                if (checked)
                    strchkbxInability = "Inability to open mouth";
                else
                    strchkbxInability = null;

                return strchkbxInability;

            case R.id.chkbxDiscolored:
                if (checked)
                    strchkbxDiscolored = "Discolored oral mucosa";
                else
                    strchkbxDiscolored = null;

                return strchkbxDiscolored;

            case R.id.chkbxTeeth:
                if (checked)
                    strchkbxTeeth = "Teeth Alignment issues";
                else
                    strchkbxTeeth = null;
                return strchkbxTeeth;

            case R.id.chkbxSwelling:
                if (checked)
                    strchxbxSwelling = "Swelling";
                else
                    strchxbxSwelling = null;

                return strchxbxSwelling;

            case R.id.ckkbxgrowth:
                if (checked)
                    strchxbxGrowth = "Growth";
                else
                    strchxbxGrowth = null;

                return strchxbxGrowth;

            case R.id.chkbxPain:
                if (checked)
                    strchxbxPain = "Pain";
                else
                    strchxbxPain = null;

                return strchxbxPain;

            case R.id.chkbxrightjaw:
                if (checked)
                    strchkbxrightjaw = "Right Jaw";
                else
                    strchkbxrightjaw = null;

                return strchkbxrightjaw ;

            case R.id.chkbxleftjaw:
                if (checked)
                    strchkbxleftjaw = "Left Jaw";
                else
                    strchkbxleftjaw = null;

                return strchkbxleftjaw;
            case R.id.chkbxupperjaw:
                if (checked)
                    strchkbxupperjaw = "Upper Jaw";
                else
                    strchkbxupperjaw = null;

                return strchkbxupperjaw;

            case R.id.chkbxlowerjaw:
                if (checked)
                    strchkbxlowerjaw = "Lower Jaw";
                else
                    strchkbxlowerjaw = null;

                return strchkbxlowerjaw;
        }
        return null;
    }

    public String onRadioButtonClicked(View view) {
        TextInputLayout tilotherPainIncrease = (TextInputLayout)findViewById(R.id.tftilay_otherPainIncrease);
        TextInputLayout tilotherPainDecrease = (TextInputLayout)findViewById(R.id.tftilay_otherPainDecrease);
        TextInputLayout tietdayslayout = (TextInputLayout)findViewById(R.id.tftilayout_sufferingdays);
        TextInputEditText tilotherPainIncreaseText = (TextInputEditText)findViewById(R.id.tftilay_otherPainIncreaseValue);
        TextInputEditText tilotherPainDecreaseText = (TextInputEditText)findViewById(R.id.tftilay_otherPainDecreaseValue);
        tietdays = (TextInputEditText) findViewById(R.id.tftilay_sufferingdays);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            //suffering from
            case R.id.radio_day:
                if (checked){
                    strSufferingTime = "Days";
                    tietdayslayout.setVisibility(View.VISIBLE);
                    tietdayslayout.requestFocus();
                }
                else
                    strSufferingTime = null;

                return strSufferingTime;


            case R.id.radio_week:
                if (checked){
                    strSufferingTime = "Week";
                    tietdayslayout.setVisibility(View.GONE);
                    tietdays.setText("");
                }
                else
                    strSufferingTime = null;

                return strSufferingTime;

            case R.id.radio_month:
                if (checked){
                    strSufferingTime = "Month";
                    tietdayslayout.setVisibility(View.GONE);
                    tietdays.setText("");
                }
                else
                    strSufferingTime = null;
                return strSufferingTime;

            case R.id.radio_years:
                if (checked){
                    strSufferingTime = "Year";
                    tietdayslayout.setVisibility(View.GONE);
                    tietdays.setText("");
                }
                else
                    strSufferingTime = null;
                return strSufferingTime;


            //Pain Type 1
            case R.id.radio_sharp:
                if (checked)
                    strPainType = "Sharp";
                else
                    strPainType = null;
                return strPainType;

            case R.id.radio_dull:
                if (checked)
                    strPainType = "Dull";
                else
                    strPainType = null;
                return strPainType;

                case R.id.radio_throbbing:
                if (checked)
                    strPainType = "Throbbing";
                else
                    strPainType = null;
                    return strPainType;

            case R.id.radio_nopain:
                if (checked)
                    strPainType = "No pain";
                else
                    strPainType = null;
                return strPainType;

                    //Pain type 2

            case R.id.radio_continuous:
                if (checked)
                    strPainType2 = "Continuous";
                else
                    strPainType2 = null;
                return strPainType2;


            case R.id.radio_intermittent:
                if (checked)
                    strPainType2 = "Intermittent";
                else
                    strPainType2 = null;
                return strPainType2;

            //Pain Increase on
            case R.id.radio_hotfood:
                if (checked) {
                    strPainIncrease = "Hot Food";
                    tilotherPainIncrease.setVisibility(View.GONE);
                    tilotherPainIncreaseText.setText("");
                }
                    else
                        strPainIncrease = null;

                return strPainIncrease;

            case R.id.radio_coldfood:
                if (checked){
                    strPainIncrease = "Cold Food";
                    tilotherPainIncrease.setVisibility(View.GONE);
                    tilotherPainIncreaseText.setText("");
                }
                else
                    strPainIncrease = null;

                return strPainIncrease;

            case R.id.radio_NA:
                if (checked){
                    strPainIncrease = "Not Applicable";
                    tilotherPainIncrease.setVisibility(View.GONE);
                    tilotherPainIncreaseText.setText("");
                }
                else
                    strPainIncrease = null;

                return strPainIncrease;

            case R.id.radio_other:
                if (checked) {
                strPainIncrease = "OtherPainIncrease";
                tilotherPainIncrease.setVisibility(View.VISIBLE);
                tilotherPainIncrease.requestFocus();
                }
                    else
            strPainIncrease = null;

            return strPainIncrease;

                //Pain Decrease on
            case R.id.radio_hotfooddec:
                if (checked) {
                    strPainDecrease = "Hot Food";
                    tilotherPainDecrease.setVisibility(View.GONE);
                    tilotherPainDecreaseText.setText("");
                }
                else
                    strPainDecrease = null;

                return strPainDecrease;

            case R.id.radio_coldfooddec:
                if (checked){
                    strPainDecrease = "Cold Food";
                    tilotherPainDecrease.setVisibility(View.GONE);
                    tilotherPainDecreaseText.setText("");
                }
                else
                    strPainDecrease = null;

                return strPainDecrease;

            case R.id.radio_otherNAdec:
                if (checked){
                    strPainDecrease = "Not Applicable";
                    tilotherPainDecrease.setVisibility(View.GONE);
                    tilotherPainDecreaseText.setText("");
                }
                else
                    strPainDecrease = null;

                return strPainDecrease;


            case R.id.radio_otherdec:
                if (checked) {
                    strPainDecrease = "OtherPainDecrease";
                    tilotherPainDecrease.setVisibility(View.VISIBLE);
                    tilotherPainDecrease.requestFocus();
                }
                else
                    strPainDecrease = null;

                return strPainDecrease;

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
                            Intent LoginIntent = new Intent(Complain.this, HomePage.class);
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

        String stringChkboxckdSuffering,stringChkboxckdTime,stretPrblm;
        CheckBox chkbxSwelling,rightjaw;
        RadioButton time,pain,inc,dec;

        etPrblm = (EditText)findViewById(R.id.etPrblm);
        chkbxSwelling = (CheckBox)findViewById(R.id.chkbxSwelling);
        rightjaw = (CheckBox)findViewById(R.id.chkbxrightjaw);
        time = (RadioButton)findViewById(R.id.radio_week);
        pain = (RadioButton)findViewById(R.id.radio_sharp);
        inc = (RadioButton)findViewById(R.id.radio_hotfood);
        dec = (RadioButton)findViewById(R.id.radio_hotfooddec);
        tilotherPainIncrease = (TextInputEditText)findViewById(R.id.tftilay_otherPainIncreaseValue);
        tilotherPainDecrease = (TextInputEditText)findViewById(R.id.tftilay_otherPainDecreaseValue);
        strtilotherPainIncrease = String.valueOf(tilotherPainIncrease.getText());
        strtilotherPainDecrease = String.valueOf(tilotherPainDecrease.getText());
        tietdays = (TextInputEditText) findViewById(R.id.tftilay_sufferingdays);

        stretPrblm = String.valueOf(etPrblm.getText());
        stretdays = String.valueOf(tietdays.getText());


        if(v.getId ()==R.id.buttoncomplain){

            stringChkboxckdSuffering = strchkbxBleedingGums+(strchkbxInability)+(strchkbxDiscolored)+(strchkbxTeeth)+(strchxbxSwelling)
                    +(strchxbxGrowth)+(strchxbxPain);

            stringChkboxckdTime = strchkbxrightjaw+(strchkbxleftjaw)+(strchkbxupperjaw)+(strchkbxlowerjaw);

            if (stretPrblm.isEmpty()) {
                etPrblm.setError("Please describe your problem");
                etPrblm.requestFocus();
            }
            else if (stringChkboxckdSuffering.equals("nullnullnullnullnullnullnull")) {
                Toast.makeText(this, "Please select at least one problem you are suffering from", Toast.LENGTH_LONG).show();
                chkbxSwelling.requestFocus();
            }
            else if(stringChkboxckdTime.equals("nullnullnullnull")) {
                Toast.makeText(this, "Please select in which jaw you have problem", Toast.LENGTH_LONG).show();
                rightjaw.requestFocus();
            }
//            else if (stretdays.isEmpty()) {
//                tietdays.setError("Please Enter the no.of day");
//                tietdays.requestFocus();
//            }
            else if (strSufferingTime == null){
                Toast.makeText(this, "Please select from when you are suffering from", Toast.LENGTH_SHORT).show();
                time.requestFocus();
            }
            else if (strSufferingTime.equals("Days") && (stretdays.isEmpty())){
                tietdays.setError("Please enter no of days you are suffering from");
                tietdays.requestFocus();
            }
            else if (strPainType == null){
                Toast.makeText(this, "Please select type of pain your suffering from", Toast.LENGTH_SHORT).show();
                pain.requestFocus();
            }
            else if (strPainType2 == null){
                Toast.makeText(this, "Please select type of pain your suffering from Intermittent/Continuous", Toast.LENGTH_SHORT).show();
                pain.requestFocus();
            }
            else if (strPainIncrease == null){
                Toast.makeText(this, "Please select pain increase reason", Toast.LENGTH_SHORT).show();
                inc.requestFocus();
            }
            else if (strPainIncrease.equals("OtherPainIncrease") && (strtilotherPainIncrease.isEmpty())){
                    tilotherPainIncrease.setError("Please enter pain increase reason");
                    tilotherPainIncrease.requestFocus();
            }
            else if (strPainDecrease == null){
                Toast.makeText(this, "Please select pain decrease reason", Toast.LENGTH_SHORT).show();
                dec.requestFocus();
            }
            else if (strPainDecrease.equals("OtherPainDecrease") && (strtilotherPainDecrease.isEmpty())){
                    tilotherPainDecrease.setError("Please enter pain decrease reason");
                    tilotherPainDecrease.requestFocus();
            }
            else{
                loadingBar.setTitle("Saving Details");
                loadingBar.setMessage("Please wait, While we are saving your details.");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);
                StoreComplainData();
            }
        }
    }

    private void StoreComplainData() {

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child(currentDate);

        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("ComplainBleedingGums", strchkbxBleedingGums);
        userMap.put("ComplainInability", strchkbxInability);
        userMap.put("ComplainDiscolored", strchkbxDiscolored);
        userMap.put("ComplainTeeth", strchkbxTeeth);
        userMap.put("ComplainCity", strchxbxSwelling);
        userMap.put("ComplainGrowth", strchxbxGrowth);
        userMap.put("ComplainPain", strchxbxPain);
        userMap.put("Complainrightjaw", strchkbxrightjaw);
        userMap.put("Complainleftjaw", strchkbxleftjaw);
        userMap.put("Complainupperjaw", strchkbxupperjaw);
        userMap.put("Complainlowerjaw", strchkbxlowerjaw);
        userMap.put("Complaindays", stretdays);
        userMap.put("ComplainSufferingTime", strSufferingTime);
        userMap.put("ComplainPainType", strPainType);
        userMap.put("ComplainPainType2", strPainType2);
        userMap.put("ComplainPainIncrease", strPainIncrease);
        userMap.put("ComplainotherPainIncrease", strtilotherPainIncrease);
        userMap.put("ComplainPainDecrease", strPainDecrease);
        userMap.put("ComplainotherPainDecrease", strtilotherPainDecrease);

        UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    loadingBar.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder;

//                    Toast.makeText(Info.this, "Done", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Complain.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Complain.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    }
                    builder.setTitle("Confirmation")
                            .setIcon(R.drawable.ic_info)
                            .setMessage("Basic Complain Details Saved. \nPlease fill other forms")
                            .setPositiveButton("Ok, Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    dialog.dismiss();
                                    Intent LoginIntent = new Intent(Complain.this, History_one.class);
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
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Complain.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new androidx.appcompat.app.AlertDialog.Builder(Complain.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
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