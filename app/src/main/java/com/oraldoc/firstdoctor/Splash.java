package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        timer = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    synchronized (this) {
//                        wait(2000);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    Intent intent = new Intent(Splash.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };
//        timer.start();
    }
    @Override
    public void onStart() {
        mAuth = FirebaseAuth.getInstance();
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            SendUserToHomePage();
        }
        else{
            SendUserToSignInActivity();
        }
    }
    private void SendUserToHomePage() {
        Intent LoginIntent = new Intent(this, HomePage.class);
//        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(LoginIntent);
        finish();
    }

    private void SendUserToSignInActivity() {
        Intent LoginIntent = new Intent(this, MainActivity.class);
//        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(LoginIntent);
        finish();
    }
}