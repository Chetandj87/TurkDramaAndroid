package com.loveseries.tdrama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;

public class DramasActivity extends AppCompatActivity implements DramasAdapter.OnDramaListClick {

    private FirebaseFirestore firebaseFirestore;
    private DramasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dramas);

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        boolean pushNotification = sharedPreferences.getBoolean("value",true);

        if(pushNotification){
            //Firebase Notification
            firebaseNotification();
        }

        firebaseFirestore = FirebaseFirestore.getInstance();
        RecyclerView dramaRecyclerList = findViewById(R.id.dramaList);

        //Query
        Query query = firebaseFirestore.collection("dramas").orderBy("title", Query.Direction.ASCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(3)
                .setPageSize(3)
                .build();

        //RecyclerOptions
        FirestorePagingOptions<Drama> options = new FirestorePagingOptions.Builder<Drama>()
                .setLifecycleOwner(this)
                .setQuery(query, config, Drama.class)
                .build();

        adapter = new DramasAdapter(options, this);

        dramaRecyclerList.setHasFixedSize(true);
        dramaRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        dramaRecyclerList.setAdapter(adapter);
    }

    @Override
    public void onDramaClick(DocumentSnapshot snapshot, int position) {
        Log.d("Drama Clicked","Drama ID "+snapshot.getId()+" Drama Name "+snapshot.get("title")+snapshot.get("description"));
        Drama drama = new Drama();
        drama.setId(snapshot.getId());
        drama.setTitle((String) snapshot.get("title"));
        drama.setDescription((String) snapshot.get("description"));
        drama.setImageURL((String) snapshot.get("imageURL"));

        Intent intent = new Intent(this, EpisodesActivity.class);
        intent.putExtra("drama",drama);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:{
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void firebaseNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("Drama","Drama", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}