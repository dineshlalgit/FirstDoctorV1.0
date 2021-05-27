package com.oraldoc.firstdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {
    TextView signUp_txt;
    AppCompatButton signIn_bt, google_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUp_txt = findViewById(R.id.signUp);
        signIn_bt = findViewById(R.id.signIn_button);
        google_bt = findViewById(R.id.google_button);
        signIn_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent SignInB = new Intent(MainActivity.this, HomePage.class);
//                startActivity(SignInB);
            }
        });
        google_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignInGoogleB = new Intent(MainActivity.this, Info.class);
                startActivity(SignInGoogleB);
            }
        });
        signIn_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn_sucess = new Intent(MainActivity.this, HomePage.class);
                startActivity(signIn_sucess);
            }
        });
        signUp_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(SignUp);
            }
        });

    }
}