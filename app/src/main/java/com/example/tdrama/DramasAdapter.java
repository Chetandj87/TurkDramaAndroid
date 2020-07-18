package com.example.tdrama;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DramasAdapter extends RecyclerView.Adapter<DramasAdapter.DramaViewHolder> {

    private Context mCtx;
    private List<Drama> dramaList;

    public DramasAdapter(Context mCtx, List<Drama> dramaList){
        this.mCtx=mCtx;
        this.dramaList=dramaList;
    }

    @NonNull
    @Override
    public DramaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DramaViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_drama, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DramaViewHolder holder, int position) {
        Drama drama = dramaList.get(position);

        holder.textView_name.setText(drama.getTitle());
        Picasso.get().load(drama.getImageURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dramaList.size();
    }

    class DramaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView_name;
        ImageView imageView;

        public DramaViewHolder(View itemView){
            super(itemView);

            textView_name = itemView.findViewById(R.id.textView_name);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Drama drama = dramaList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, EpisodesActivity.class);
            intent.putExtra("drama",drama);
            mCtx.startActivity(intent);
        }
    }
}
