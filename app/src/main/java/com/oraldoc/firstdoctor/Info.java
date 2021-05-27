package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Info extends AppCompatActivity {
    AppCompatButton next_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        next_bt = findViewById(R.id.Next_button);
        next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextB = new Intent(Info.this, Complain.class);
                startActivity(nextB);
            }
        });
    }
}