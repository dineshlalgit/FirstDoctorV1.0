package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class Examination_one extends AppCompatActivity {
    private String strLips,strTongue,strMouthfloor,strCheeks,strMouthroof,strGums,strChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_one);
    }

    public String onCheckboxClicked(View view) {
        // Is the view now checked?

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.chkbxLips:
                if (checked)
                    strLips = "Lips";
                else
                    strLips = null;
                return strLips;

            case R.id.ckbxTongue:
                if (checked)
                    strTongue = "Tongue";
                else
                    strTongue = null;

                return strTongue;

            case R.id.chkbxMouthfloor:
                if (checked)
                    strMouthfloor = "Floor of mouth";
                else
                    strMouthfloor = null;

                return strMouthfloor;

            case R.id.chkbxCheeks:
                if (checked)
                    strCheeks = "Cheeks";
                else
                    strCheeks = null;
                return strCheeks;

            case R.id.chkbxMouthroof:
                if (checked)
                    strMouthroof = "Roof of mouth";
                else
                    strMouthroof = null;

                return strMouthroof;

            case R.id.chkbxGums:
                if (checked)
                    strGums = "Gums";
                else
                    strGums = null;

                return strGums;
        }
        return null;
    }

    public void onNextButtonClick(View v){
        strChecked = strLips+(strTongue)+(strMouthfloor)+(strCheeks)+(strMouthroof)+(strGums);

        if(v.getId ()==R.id.btnhistory){
            if(strChecked.equals("nullnullnullnullnullnull")){
                Toast.makeText(this, "Please select one place to view", Toast.LENGTH_LONG).show();
            }
            else{
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
}