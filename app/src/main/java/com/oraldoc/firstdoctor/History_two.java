package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputLayout;

public class History_two extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_two);
    }
    public void onNextButtonClick(View v){
        if(v.getId ()==R.id.btnhistory){
            Intent nxt = new Intent(History_two.this,Examination_one.class);
            startActivity(nxt);
        }
    }
    public void onRadioButtonClicked(View view) {
        TextInputLayout tilother = (TextInputLayout)findViewById(R.id.tftilay_otherchew);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_one:
                if (checked)
                    break;
            case R.id.radio_two:
                if (checked)
                    break;
            case R.id.radio_dont:
                if (checked)
                    break;
            case R.id.radio_flossyes:
                if (checked)
                    break;

            case R.id.radio_flossno:
                if (checked)
                    break;

            case R.id.radio_mouthwashyes:
                if (checked)
                    break;

            case R.id.radio_mouthwashno:
                if (checked)
                    break;

            case R.id.radio_tobaccoyes:
                if (checked)
                    break;
            case R.id.radio_tobaccono:
                if (checked)
                    break;

            case R.id.radio_cigarette:
                if (checked)
                    break;
            case R.id.radio_bedi:
                if (checked)
                    break;
            case R.id.radio_cigar:
                if (checked)
                    break;
            case R.id.radio_hookka:
                if (checked)
                    break;
            case R.id.radio_pan:
                if (checked)
                    tilother.setVisibility(View.GONE);
                break;

            case R.id.radio_chewtobacco:
                if (checked)
                    tilother.setVisibility(View.GONE);
                break;

            case R.id.radio_supari:
                if (checked)
                    tilother.setVisibility(View.GONE);
                break;
            case R.id.radio_jhartha:
                if (checked)
                    tilother.setVisibility(View.GONE);
                break;
            case R.id.radio_sukha:
                if (checked)
                    tilother.setVisibility(View.GONE);
                break;
            case R.id.radio_chewtypeother:

                if (checked)
                    tilother.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_less5:
                if (checked)
                    break;
            case R.id.radio_more5:
                if (checked)
                    break;
            case R.id.radio_more10:
                if (checked)
                    break;
            case R.id.radio_alcoyes:
                if (checked)
                    break;
            case R.id.radio_alcono:
                if (checked)
                    break;
            case R.id.radio_less1:
                if (checked)
                    break;
            case R.id.radio_more1:
                if (checked)
                    break;
            case R.id.radio_more3:
                if (checked)
                    break;

            case R.id.radio_other:
                if (checked)
                    tilother.setVisibility(View.VISIBLE);
                break;

        }
    }
}