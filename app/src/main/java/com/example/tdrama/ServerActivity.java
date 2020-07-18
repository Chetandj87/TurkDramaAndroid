package com.example.tdrama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServerActivity extends AppCompatActivity {

    private Episode episode;
    private TextView textView_episodeNumber;
    private Button button_Server1,button_Server2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        episode = (Episode) getIntent().getSerializableExtra("episode");

        textView_episodeNumber = findViewById(R.id.textView_EpisodeNumber);
        button_Server1 = (Button) findViewById(R.id.button_server1);
        button_Server2 = (Button) findViewById(R.id.button_server2);

        textView_episodeNumber.setText("Episode "+episode.getNumber());

        button_Server1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}