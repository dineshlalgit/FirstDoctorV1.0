package com.oraldoc.firstdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputLayout;

public class Complain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
    }
    public void onNextButtonClick(View v){
        if(v.getId ()==R.id.buttoncomplain){
            Intent nxt = new Intent(Complain.this,History_one.class);
            startActivity(nxt);
        }
    }
    public void onRadioButtonClicked(View view) {
        TextInputLayout tilother = (TextInputLayout)findViewById(R.id.tftilay_other);
        TextInputLayout tilotherdec = (TextInputLayout)findViewById(R.id.tftilay_otherdec);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_rightjaw:
                if (checked)
                    break;
            case R.id.radio_leftjaw:
                if (checked)
                    break;
            case R.id.radio_upperjaw:
                if (checked)
                    break;
            case R.id.radio_lowerjaw:
                if (checked)
                    break;

            case R.id.radio_week:
                if (checked)
                    break;
            case R.id.radio_month:
                if (checked)
                    break;
            case R.id.radio_years:
                if (checked)
                    break;

            case R.id.radio_sharp:
                if (checked)
                    break;
            case R.id.radio_dull:
                if (checked)
                    break;
            case R.id.radio_throbbing:
                if (checked)
                    break;
            case R.id.radio_continuous:
                if (checked)
                    break;
            case R.id.radio_nopain:
                if (checked)
                    break;

            case R.id.radio_intermittent:
                if (checked)
                    break;

            case R.id.radio_hotfood:
                if (checked)
                    tilother.setVisibility(View.GONE);
                tilotherdec.setVisibility(View.GONE);
                break;
            case R.id.radio_coldfood:
                if (checked)
                    tilother.setVisibility(View.GONE);
                tilotherdec.setVisibility(View.GONE);
                break;
            case R.id.radio_other:
                if (checked)
                    tilother.setVisibility(View.VISIBLE);

                break;

            case R.id.radio_hotfooddec:
                if (checked)
                    tilother.setVisibility(View.GONE);
                tilotherdec.setVisibility(View.GONE);
                break;
            case R.id.radio_coldfooddec:
                if (checked)
                    tilother.setVisibility(View.GONE);
                tilotherdec.setVisibility(View.GONE);
                break;

            case R.id.radio_otherdec:
                if (checked)
                    tilotherdec.setVisibility(View.VISIBLE);
                break;

        }
    }
}