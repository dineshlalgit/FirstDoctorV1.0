package com.oraldoc.firstdoctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Complain extends AppCompatActivity {
    private EditText etPrblm;
    private CheckBox chkbxBleedingGums,chkbxInability,chkbxDiscolored,chkbxTeeth,chxbxSwelling,chxbxGrowth,chxbxPain,chkbxrightjaw,chkbxleftjaw,chkbxupperjaw,chkbxlowerjaw;
    private TextInputEditText tilotherPainIncrease,tilotherPainDecrease,tietdays;
    private String
            strchkbxBleedingGums,strchkbxInability,strchkbxDiscolored,strchkbxTeeth,strchxbxSwelling, strchxbxGrowth,strchxbxPain,strchkbxrightjaw,strchkbxleftjaw,strchkbxupperjaw,strchkbxlowerjaw,
            strtilotherPainIncrease,strtilotherPainDecrease,stretdays,
            strSufferingTime,strPainType,strPainIncrease,strPainDecrease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
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
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            //suffering from
            case R.id.radio_week:
                if (checked)
                    strSufferingTime = "Week";
                else
                    strSufferingTime = null;

                return strSufferingTime;

            case R.id.radio_month:
                if (checked)
                    strSufferingTime = "Month";
                else
                    strSufferingTime = null;
                return strSufferingTime;

            case R.id.radio_years:
                if (checked)
                    strSufferingTime = "Year";
                else
                    strSufferingTime = null;
                return strSufferingTime;


            //Pain Type
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

            case R.id.radio_continuous:
                if (checked)
                    strPainType = "Continuous";
                else
                    strPainType = null;
                return strPainType;

            case R.id.radio_nopain:
                if (checked)
                    strPainType = "No pain";
                else
                    strPainType = null;
                return strPainType;

            case R.id.radio_intermittent:
                if (checked)
                    strPainType = "Intermittent";
                else
                    strPainType = null;
                return strPainType;

            //Pain Increase on
            case R.id.radio_hotfood:
                if (checked) {
                    strPainIncrease = "Hot Food";
                    tilotherPainIncrease.setVisibility(View.GONE);
                }
                    else
                        strPainIncrease = null;

                return strPainIncrease;

            case R.id.radio_coldfood:
                if (checked){
                    strPainIncrease = "Cold Food";
                    tilotherPainIncrease.setVisibility(View.GONE);
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
                }
                else
                    strPainDecrease = null;

                return strPainDecrease;

            case R.id.radio_coldfooddec:
                if (checked){
                    strPainDecrease = "Cold Food";
                    tilotherPainDecrease.setVisibility(View.GONE);
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
            else if (stretdays.isEmpty()) {
                tietdays.setError("Please Enter the no.of day");
                tietdays.requestFocus();
            }
            else if (strSufferingTime == null){
                Toast.makeText(this, "Please select from when you are suffering from", Toast.LENGTH_SHORT).show();
                time.requestFocus();
            }
            else if (strPainType == null){
                Toast.makeText(this, "Please select type of pain your suffering from", Toast.LENGTH_SHORT).show();
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
                Intent nxt = new Intent(Complain.this,History_one.class);
                startActivity(nxt);
            }
        }
    }
}