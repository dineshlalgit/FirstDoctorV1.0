package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Examination_one extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_one);
    }
    public void onNextButtonClick(View v){

        if(v.getId ()==R.id.btnhistory){
            androidx.appcompat.app.AlertDialog.Builder builder;
            builder = new androidx.appcompat.app.AlertDialog.Builder(Examination_one.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);

            builder.setTitle("Confirmation")
//                .setIcon(R.drawable.ic_info)
                .setMessage("Your Details is submitted to our Doctors successfully, please wait till we get back to you with the solution")
                .setPositiveButton("Ok, Visit Home", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                        Intent LoginIntent = new Intent(Examination_one.this, HomePage.class);
                        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(LoginIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
        }
    }
}