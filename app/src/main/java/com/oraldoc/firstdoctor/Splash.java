package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;

import java.util.Timer;
import java.util.TimerTask;

import ch.halcyon.squareprogressbar.SquareProgressBar;

public class Splash extends AppCompatActivity {
    //    private ProgressBar progressBar;
    private SquareProgressBar squareProgressBar;
    private Timer timer;
//    NoInternetDialog noInternetDialog;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        Dialog dialog = new Dialog(this);
//        progressBar = findViewById(R.id.progressBar);
        squareProgressBar = findViewById(R.id.sprogressbar);
        squareProgressBar.setImage(R.drawable.fd);
        squareProgressBar.setRoundedCorners(true);
        onSplash();
    }
    private void onSplash() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i < 100) {
                    runOnUiThread(() -> {

                    });
                    squareProgressBar.setProgress(i);
                    i++;
                } else {
                    timer.cancel();
                    CheckLogin();
                }
            }
        }, 0, 50);
    }

    public void CheckLogin() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        super.onStart();
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

//    @Override
//    protected void onResume() {
//        super.onResume();
////        NoInternetDialog noInternetDialog;
//
//        NoInternetDialog.Builder builder1 = new NoInternetDialog.Builder(this);
//
//        builder1.setConnectionCallback(new ConnectionCallback() { // Optional
//            @Override
//            public void hasActiveConnection(boolean hasActiveConnection) {
//                Toast.makeText(Splash.this, "You have internet", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder1.setCancelable(false); // Optional
//        builder1.setNoInternetConnectionTitle("No Internet"); // Optional
//        builder1.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
//        builder1.setShowInternetOnButtons(true); // Optional
//        builder1.setPleaseTurnOnText("Please turn on"); // Optional
//        builder1.setWifiOnButtonText("Wifi"); // Optional
//        builder1.setMobileDataOnButtonText("Mobile data"); // Optional
//
//        builder1.setOnAirplaneModeTitle("No Internet"); // Optional
//        builder1.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
//        builder1.setPleaseTurnOffText("Please turn off"); // Optional
//        builder1.setAirplaneModeOffButtonText("Airplane mode"); // Optional
//        builder1.setShowAirplaneModeOffButtons(true); // Optional
//
//        noInternetDialog = builder1.build();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (noInternetDialog != null) {
//            noInternetDialog.destroy();
//        }
//    }
}