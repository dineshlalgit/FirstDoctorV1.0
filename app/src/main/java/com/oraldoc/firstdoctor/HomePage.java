package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }
    public void onNextButtonClick(View v){
        if(v.getId ()==R.id.btnfillform)
        {
            Intent nxt = new Intent(HomePage.this,Info.class);
            startActivity(nxt);
        }
        else if(v.getId ()==R.id.btnconult)
        {
            Toast.makeText(HomePage.this, "Button in progress", Toast.LENGTH_SHORT).show();
        }
    }

}