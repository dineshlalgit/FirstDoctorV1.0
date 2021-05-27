package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class History_two extends AppCompatActivity {
    AppCompatButton next_hb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_two);
        next_hb=findViewById(R.id.nextH_button);
        next_hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextB = new Intent(History_two.this, Examination_one.class);
                startActivity(nextB);
            }
        });
    }
}