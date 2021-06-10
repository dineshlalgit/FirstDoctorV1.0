package com.oraldoc.firstdoctor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView signUp_txt;
    AppCompatButton signIn_bt, google_bt;
    private TextInputEditText tietEmail,tietPassword;
    private String stringEmail,stringPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUp_txt = findViewById(R.id.signUp);
        signIn_bt = findViewById(R.id.signIn_button);
//        google_bt = findViewById(R.id.google_button);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

//        signIn_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent SignInB = new Intent(MainActivity.this, HomePage.class);
////                startActivity(SignInB);
//            }
//        });
//        google_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(MainActivity.this, "Google sign button in progress", Toast.LENGTH_SHORT).show();
//                Intent SignInGoogleB = new Intent(MainActivity.this, Complain.class);
//                startActivity(SignInGoogleB);
//            }
//        });
//        signIn_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signIn_sucess = new Intent(MainActivity.this, HomePage.class);
//                startActivity(signIn_sucess);
//            }
//        });
        signUp_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(SignUp);
            }
        });

    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentuser = mAuth.getCurrentUser();
//        if(currentuser != null){
//            CheckUserExistence();
//        }
//    }
//    private void CheckUserExistence() {
//        final String current_user_id = mAuth.getCurrentUser().getUid();
//        UsersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.hasChild(current_user_id)) {
//                    SendUserToSignUpActivity();
//                }
//                else {
//                    SendUserToHomePage();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
    private void SendUserToHomePage() {
        Intent LoginIntent = new Intent(this,HomePage.class);
//        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(LoginIntent);
        finish();
    }
    private void SendUserToSignUpActivity() {
        Intent LoginIntent = new Intent(this,SignUp.class);
//        LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(LoginIntent);
        finish();
    }
    public void onButtonClick(View v){
        if(v.getId() == R.id.signIn_button){

            tietEmail = (TextInputEditText) findViewById(R.id.tietEmail);
            tietPassword = (TextInputEditText) findViewById(R.id.tietPassword);

            stringEmail = tietEmail.getText().toString();
            stringPassword = tietPassword.getText().toString();

            if (stringEmail.isEmpty() || stringEmail.length()<5 || (!stringEmail.matches("[@.a-z0-9A-Z]*"))){
                tietEmail.setError("Mandatory field / Invalid Input");
                tietEmail.requestFocus();
            }
            else if (stringPassword.isEmpty() || stringPassword.length()<8 || (!stringPassword.matches("[A-Za-z0-9]*"))){
                tietPassword.setError("Mandatory field / Invalid Input");
                tietPassword.requestFocus();
            }
            else {
//                setContentView(R.layout.activity_otpverify);
                loadingBar.setTitle("Validating Credentials");
                loadingBar.setMessage("Please wait, While we are validating your account.");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                mAuth.signInWithEmailAndPassword(stringEmail,stringPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    loadingBar.dismiss();
                                    Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                    SendUserToHomePage();
                                }
                                else{
                                    loadingBar.dismiss();
                                    String mgs = task.getException().getMessage();
                                    Toast.makeText(MainActivity.this, "Login Error.\n" +mgs , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        }
//        else if(v.getId() == R.id.btnregister){
//            Intent register = new Intent(this,RegisterActivity.class);
//            startActivity(register);
//
//        }
//        else if(v.getId() == R.id.btnverify) {
//            setContentView(R.layout.activity_login);
//        }
        else if(v.getId() == R.id.tvforgotpass){
            showRecoverPasswordDialog();
        }
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText emailet= new EditText(this);

        // write the email using which you registered
        emailet.setHint("Enter your registered email address");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String emaill=emailet.getText().toString().trim();
                if(emaill.isEmpty()){
                    emailet.setError("Please enter your email ID");
                }
                else{
                    dialog.dismiss();
                    beginRecovery(emaill);
                }
            }
        });
    }
    private void beginRecovery(String emaill) {
        loadingBar=new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // calling sendPasswordResetEmail
        // open your email and write the new
        // password and then you can login
        mAuth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if(task.isSuccessful())
                {
                    // if isSuccessful then done messgae will be shown
                    // and you can change the password
                    Toast.makeText(MainActivity.this,"Email sent please follow the link in email to rest password",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Error Occured",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(MainActivity.this,"Error Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}