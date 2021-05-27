package com.oraldoc.firstdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Complain extends AppCompatActivity {
    AppCompatButton next_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        next_b = findViewById(R.id.next_button);
        next_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextB = new Intent(Complain.this, History_one.class);
                startActivity(nextB);
            }
        });
    }
}