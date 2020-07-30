package com.loveseries.tdrama;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Switch notificationSwitch;
    TextView copyrightText, versionDetails, contactMail, shareThisApp, rateMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationSwitch = findViewById(R.id.notificationSwitch);

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        notificationSwitch.setChecked(sharedPreferences.getBoolean("value",true));

        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notificationSwitch.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    notificationSwitch.setChecked(false);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    notificationSwitch.setChecked(true);
                }
            }
        });

        copyrightText = findViewById(R.id.copyrightText);
        versionDetails = findViewById(R.id.versionDetails);
        contactMail = findViewById(R.id.contactMail);
        shareThisApp = findViewById(R.id.shareThisApp);
        rateMe = findViewById(R.id.rateMe);

        copyrightText.setText("Copyrignt @ Loveseries\nAll Rights Reserved");

        try {
            Context context = getApplicationContext();
            String version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            versionDetails.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        contactMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO_EMAILS = {"loveseries251@gmail.com"};

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, TO_EMAILS);

                startActivity(Intent.createChooser(intent, "Choose one application"));
            }
        });

        shareThisApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Download this app to watch Turkish Dramas in English Subtitles: https://play.google.com/store/apps/details?id=com.loveseries.tdrama";
                String sharesub = "TDrama";

                shareIntent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(shareIntent,"Share using"));
            }
        });

        rateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });
    }
}