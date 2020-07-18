package com.loveseries.tdrama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EpisodesActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EpisodesAdapter adapter;
    private List<Episode> episodeList;
    private TextView textView_name,textView_description;
    private ImageView imageView;

    private FirebaseFirestore db;

    private AdView mAdView;
    private Drama drama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                recyclerView.setPadding(0,0,0,mAdView.getHeight());
            }
        });

        progressBar = findViewById(R.id.progressBar);

        db = FirebaseFirestore.getInstance();

        drama = (Drama) getIntent().getSerializableExtra("drama");
        textView_name = findViewById(R.id.textView_name);
        textView_description = findViewById(R.id.textView_description);
        imageView = findViewById(R.id.imageView);

        textView_name.setText(drama.getTitle());
        textView_description.setText(drama.getDescription());
        Picasso.get().load(drama.getImageURL()).into(imageView);

        recyclerView = findViewById(R.id.recyclerview_episodes);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        episodeList = new ArrayList<>();


        CollectionReference notesCollectionRef = db.collection("episodes");

        Query notesQuery = notesCollectionRef.whereEqualTo("dramaId",drama.getId())
                .orderBy("number",Query.Direction.ASCENDING);

        notesQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Episode episode = documentSnapshot.toObject(Episode.class);
                        episode.setId(documentSnapshot.getId());
                        episodeList.add(episode);
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });

        adapter = new EpisodesAdapter(this, episodeList);

        recyclerView.setAdapter(adapter);

//        db.collection("episodes").whereEqualTo("dramaId",drama.getId()).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        progressBar.setVisibility(View.GONE);
//
//                        if (!queryDocumentSnapshots.isEmpty()){
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//
//                            for (DocumentSnapshot d: list){
//                                Episode episode = d.toObject(Episode.class);
//                                episode.setId(d.getId());
//                                episodeList.add(episode);
//                            }
//
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                });
    }
}