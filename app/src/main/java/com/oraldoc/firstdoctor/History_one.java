package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputLayout;

public class History_one extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_one);
    }
    public void onNextButtonClick(View v){
        if(v.getId ()==R.id.btnhistory){
            Intent nxt = new Intent(History_one.this,History_two.class);
            startActivity(nxt);
        }
    }
    public void onRadioButtonClicked(View view) {
        TextInputLayout tilother = (TextInputLayout)findViewById(R.id.tftilay_otherdec);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.radio_no:
                if (checked)
                    tilother.setVisibility(View.GONE);
                break;
            case R.id.radio_yes:
                if (checked)
                    tilother.setVisibility(View.VISIBLE);
                break;



        }
    }

}