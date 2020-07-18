package com.example.tdrama;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder> {
    private Context mCtx;
    private List<Episode> episodeList;

    public EpisodesAdapter(Context mCtx, List<Episode> episodeList){
        this.mCtx=mCtx;
        this.episodeList=episodeList;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_episode, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodeList.get(position);

        holder.textEpisode_view.setText("Episode "+episode.getNumber());
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textEpisode_view;

        public EpisodeViewHolder(View itemView){
            super(itemView);

            textEpisode_view = itemView.findViewById(R.id.textEpisode_number);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Episode episode = episodeList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, ServerActivity.class);
            intent.putExtra("episode",episode);
            mCtx.startActivity(intent);
        }
    }
}
