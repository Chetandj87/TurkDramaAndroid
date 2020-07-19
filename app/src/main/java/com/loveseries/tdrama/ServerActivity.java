package com.loveseries.tdrama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ServerActivity extends AppCompatActivity {

    private Episode episode;
    private TextView textView_episodeNumber;
    private Button button_Server1,button_Server2;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        prepareAD();

        episode = (Episode) getIntent().getSerializableExtra("episode");

        textView_episodeNumber = findViewById(R.id.textView_EpisodeNumber);
        button_Server1 = (Button) findViewById(R.id.button_server1);
        button_Server2 = (Button) findViewById(R.id.button_server2);

        textView_episodeNumber.setText("Episode "+episode.getNumber());

        button_Server1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                intent.putExtra("server", episode.getServer1());
                startActivity(intent);
            }
        });

        button_Server2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                intent.putExtra("server", episode.getServer2());
                startActivity(intent);
            }
        });
    }

    public void prepareAD() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6499826538736000/5331839929");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}