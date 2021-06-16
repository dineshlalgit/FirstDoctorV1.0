package com.oraldoc.firstdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class QuadMonk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quad_monk);
    }
    public void onNextButtonClick(View v) {
        if (v.getId() == R.id.btnweb) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://Quadmonk.com"));
            startActivity(intent);
        }
        else if (v.getId() == R.id.btnemail) {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"quadmonkpb@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Sending from First Doctor App regarding ");
            email.putExtra(Intent.EXTRA_TEXT, "Hi QuadMonk, ");
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        }
        else if (v.getId() == R.id.btnfb) {
            String YourPageURL = "https://www.facebook.com/n/?Quadmonkpb";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

            startActivity(browserIntent);
        }
    }

}