package com.loveseries.tdrama;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class DramasAdapter extends FirestorePagingAdapter<Drama, DramasAdapter.DramasViewHolder> {

    private OnDramaListClick onDramaListClick;

    public DramasAdapter(@NonNull FirestorePagingOptions<Drama> options, OnDramaListClick onDramaListClick) {
        super(options);
        this.onDramaListClick=onDramaListClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull DramasViewHolder holder, int position, @NonNull Drama model) {
        holder.dramaname.setText(model.getTitle());
        if (!model.getImageURL().isEmpty()){
            Picasso.get().load(model.getImageURL()).into(holder.imageView);
        }
    }

    @NonNull
    @Override
    public DramasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drama, parent, false);
        return new DramasViewHolder(view);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state){
            case FINISHED:
                break;
            case LOADING_MORE:
                break;
            case LOADING_INITIAL:
                break;
            case ERROR:
                break;
            case LOADED:
                break;
        }
    }

    public class DramasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView dramaname;
        private ImageView imageView;

        public DramasViewHolder(@NonNull View itemView) {
            super(itemView);

            dramaname = itemView.findViewById(R.id.textView_dramaname);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDramaListClick.onDramaClick(getItem(getAdapterPosition()), getAdapterPosition());
        }
    }

    public interface OnDramaListClick {
        void onDramaClick(DocumentSnapshot snapshot, int position);
    }
}
