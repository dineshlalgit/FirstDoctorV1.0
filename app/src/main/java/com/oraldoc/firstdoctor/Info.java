package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Info extends AppCompatActivity {
    private TextInputEditText tietName,tietAge,tietAddress,tietCity,tietZip,tietMobile;
    private String stringName,stringAge,stringAddress,stringCity,stringZip,stringMobile,stringGndr,stringChkbox,stringChkboxCon,stringChkboxckd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
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

            if (stringName.isEmpty() || stringName.length()<3 || (!stringName.matches("[A-Za-z0-9 ]*"))){
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
                Intent nxt = new Intent(Info.this,Complain.class);
                startActivity(nxt);
            }
        }
    }
}