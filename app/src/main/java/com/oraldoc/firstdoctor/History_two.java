package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class History_two extends AppCompatActivity {

//    private String strBrush1,strBrush2,strBrushNotDaily,strFlossYes,strFlossNo,strMouthwashYes,strMouthwashNo,strCigratte,
//                    strBedi,strCigar,strHookka,strNoSmoke,strPaan,strTobacco,strSupari,strJhartha,strSukha,strOther,
//                    strLess5,str5to10,strMore10,frequencyLess1,frequencyupto3,frequencymore3,strAlcoholYes,strAlcoholNo;

    private String strBrush,strFloss,strMouthwash,strTobacco,strSmoke,strChew,strDuration,strFrequency,strAlcohol,othervalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_two);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        TextInputLayout tilother = (TextInputLayout)findViewById(R.id.tftilay_otherchew);
        TextInputEditText tftilay_otherchewValue = (TextInputEditText)findViewById(R.id.tftilay_otherchewValue);
        // Check which radio button was clicked
        switch(view.getId()) {
            //Brush
            case R.id.radio_brushone:
                if (checked)
                    strBrush = "1 times a day";
                else
                    strBrush = null;
                break;

            case R.id.radio_brushtwo:
                if (checked)
                    strBrush = "2 times a day";
                else
                    strBrush = null;
                break;

            case R.id.radio_brushdont:
                if (checked)
                    strBrush = "Brush Daily";
                else
                    strBrush = null;
                break;

            //Floss
            case R.id.radio_flossyes:
                if (checked)
                    strFloss = "Yes";
                else
                    strFloss = null;
                break;

            case R.id.radio_flossno:
                if (checked)
                    strFloss = "No";
                else
                    strFloss = null;
                break;

            //Mouthwash

            case R.id.radio_mouthwashyes:
                if (checked)
                    strMouthwash = "Yes";
                else
                    strMouthwash = null;
                break;

            case R.id.radio_mouthwashno:
                if (checked)
                    strMouthwash = "No";
                else
                    strMouthwash = null;
                break;

            //Tobacco

            case R.id.radio_tobaccoyes:
                if (checked)
                    strTobacco = "Yes";
                else
                    strTobacco = null;
                break;

            case R.id.radio_tobaccono:
                if (checked)
                    strTobacco = "No";
                else
                    strTobacco = null;
                break;

            //Smoke
            case R.id.radio_cigarette:
                if (checked)
                    strSmoke = "Cigarette";
                else
                    strSmoke = null;
                break;

            case R.id.radio_bedi:
                if (checked)
                    strSmoke = "Bedi";
                else
                    strSmoke = null;
                break;

            case R.id.radio_cigar:
                if (checked)
                    strSmoke = "Cigar";
                else
                    strSmoke = null;
                break;

            case R.id.radio_hookka:
                if (checked)
                    strSmoke = "Hookka";
                else
                    strSmoke = null;
                break;

            case R.id.radio_nosmoke:
                if (checked)
                    strSmoke = "No";
                else
                    strSmoke = null;
                break;

            //Chewtype

            case R.id.radio_pan:
                if (checked) {
                    tilother.setVisibility(View.GONE);
                    tftilay_otherchewValue.setText("");
                    strChew = "Pan";
                }
                else
                    strChew = null;
                break;

            case R.id.radio_chewtobacco:
                if (checked) {
                    tilother.setVisibility(View.GONE);
                    tftilay_otherchewValue.setText("");
                    strChew = "Chew Tobacco";
                }
                else
                    strChew = null;
                break;

            case R.id.radio_supari:
                if (checked) {
                    tilother.setVisibility(View.GONE);
                    tftilay_otherchewValue.setText("");
                    strChew = "Arecanut (Supari)";
                }
                else
                    strChew = null;

            case R.id.radio_jhartha:
                if (checked) {
                    tilother.setVisibility(View.GONE);
                    tftilay_otherchewValue.setText("");
                    strChew = "Jhartha";
                }
                else
                    strChew = null;
                break;

            case R.id.radio_sukha:
                if (checked) {
                    tilother.setVisibility(View.GONE);
                    tftilay_otherchewValue.setText("");
                    strChew = "Suhka";
                }
                else
                    strChew = null;
                break;

            case R.id.radio_chewtypeother:
                if (checked) {
                    tftilay_otherchewValue.setText("");
                    tilother.setVisibility(View.VISIBLE);
                    strChew = "Other";
                }
                else
                    strChew = null;
                break;

            //Duration

            case R.id.radio_less5:
                if (checked)
                    strDuration = "Less then 5yrs";
                else
                    strDuration = null;
                break;

            case R.id.radio_more5:
                if (checked)
                    strDuration = "More then 5yrs";
                else
                    strDuration = null;
                break;

            case R.id.radio_more10:
                if (checked)
                    strDuration = "More then 10yrs";
                else
                    strDuration = null;
                break;

                //Frequency

            case R.id.radio_alcoyes:
                if (checked)
                    strAlcohol= "Yes";
                else
                    strAlcohol = null;
                break;

            case R.id.radio_alcono:
                if (checked)
                    strAlcohol = "No";
                else
                    strAlcohol = null;

            case R.id.radio_less1:
                if (checked)
                    strFrequency = "Less than 1 packs";
                else
                    strFrequency = null;
                break;

                //Alcohol
            case R.id.radio_more1:
                if (checked)
                    strFrequency = "1 to 3packs";
                else
                    strFrequency = null;
                break;

            case R.id.radio_more3:
                if (checked)
                    strFrequency = "More than 3 packs";
                else
                    strFrequency = null;
                break;
        }
    }

    public void onNextButtonClick(View v){
        TextInputEditText tftilay_otherchewValue = (TextInputEditText)findViewById(R.id.tftilay_otherchewValue);
        othervalue = String.valueOf(tftilay_otherchewValue.getText());

             if (strBrush == null){
                Toast.makeText(this, "Please your brushing times", Toast.LENGTH_SHORT).show();
            }
            else if (strFloss == null){
                Toast.makeText(this, "Please select Yes/No for floss teeth", Toast.LENGTH_SHORT).show();
            }
            else if (strMouthwash == null){
                Toast.makeText(this, "Please select Yes/No for Mouthwash", Toast.LENGTH_SHORT).show();
            }
            else if (strTobacco == null){
                Toast.makeText(this, "Please select Yes/No for Tobacco", Toast.LENGTH_SHORT).show();
            }
            else if (strSmoke == null){
                Toast.makeText(this, "Please select Smoke type", Toast.LENGTH_SHORT).show();
            }
            else if (strChew == null){
                Toast.makeText(this, "Please select Chew type", Toast.LENGTH_SHORT).show();
            }
            else if (strChew.equals("Other") && (othervalue.isEmpty())){
                 tftilay_otherchewValue.setError("Please enter types");
                 tftilay_otherchewValue.requestFocus();
            }
            else if (strDuration == null){
                Toast.makeText(this, "Please select Duration", Toast.LENGTH_SHORT).show();
            }
            else if (strFrequency == null){
                Toast.makeText(this, "Please select Frequency", Toast.LENGTH_SHORT).show();
            }
            else if (strAlcohol == null){
                Toast.makeText(this, "Please select Yes/No for Alcohol Habit", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent nxt = new Intent(History_two.this,Examination_one.class);
                startActivity(nxt);
            }

    }

}