package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class History_one extends AppCompatActivity {

    private String strHyper,strMedication,strBleeding,strCardiac,strGastric,strSurgery,strAllergy,strAsthama,strJaundice,strDiabetic,strEpilepsy,strOtherCondition,strOtherConditionValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_one);
    }

    public String onCheckboxClicked(View view) {
        // Is the view now checked?

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
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
                    strAsthama = "Asthma";
                else
                    strAsthama = null;

                return strAsthama ;

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

    public void onRadioButtonClicked(View view) {
        TextInputLayout tilother = (TextInputLayout)findViewById(R.id.tftilay_othercondition);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.radio_no:
                if (checked) {
                    tilother.setVisibility(View.GONE);
                    strOtherCondition = "No";
                }
                else
                    strOtherCondition = null;

                break;
            case R.id.radio_yes:
                if (checked) {
                    strOtherCondition = "Yes";
                    tilother.setVisibility(View.VISIBLE);
                    tilother.requestFocus();
                }
                else
                    strOtherCondition = null;
                break;
        }
    }

    public void onNextButtonClick(View v){

        TextInputEditText tiet_otherconditionValue;
        tiet_otherconditionValue = (TextInputEditText) findViewById(R.id.tiet_otherconditionValue);

        strOtherConditionValue = String.valueOf(tiet_otherconditionValue.getText());


        if(v.getId ()==R.id.btnhistory){

            String strCondition;

            strCondition = strHyper+(strMedication)+(strBleeding)+(strCardiac)+(strGastric)+(strSurgery)+(strAllergy)+(strAsthama)+(strJaundice)+(strDiabetic)+(strEpilepsy);

             if (strCondition.equals("nullnullnullnullnullnullnullnullnullnullnull")) {
                Toast.makeText(this, "Please select at least one condition that you are suffering from", Toast.LENGTH_LONG).show();
            }
            else if (strOtherCondition == null){
                Toast.makeText(this, "Please select Yes/No from any other conditions", Toast.LENGTH_SHORT).show();
            }
            else if (strOtherCondition.equals("Yes") && (strOtherConditionValue.isEmpty())){
                 tiet_otherconditionValue.setError("Please enter pain increase reason");
                 tiet_otherconditionValue.requestFocus();
            }
            else{
                Intent nxt = new Intent(History_one.this,History_two.class);
                startActivity(nxt);
            }
        }
    }

}