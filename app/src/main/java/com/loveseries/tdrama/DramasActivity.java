package com.loveseries.tdrama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DramasActivity extends AppCompatActivity implements DramasAdapter.OnDramaListClick {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private DramasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dramas);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestoreList);

        //Query
        Query query = firebaseFirestore.collection("dramas");

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

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);
    }

    @Override
    public void onDramaClick(DocumentSnapshot snapshot, int position) {
        Log.d("Drama Clicked","Drama ID "+snapshot.getId()+" Drama Name "+snapshot.get("title")+snapshot.get("description"));
        Drama drama = new Drama();
        drama.setId(snapshot.getId());
        drama.setTitle((String) snapshot.get("title"));
        drama.setDescription((String) snapshot.get("description"));
        drama.setImageURL((String) snapshot.get("imageURL"));

        Drama drama1 = snapshot.toObject(Drama.class);

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
            case R.id.contact_us: {
                String[] TO_EMAILS = {"loveseries251@gmail.com"};

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, TO_EMAILS);

                startActivity(Intent.createChooser(intent, "Choose one application"));
            }
        }
        return super.onOptionsItemSelected(item);
    }
}