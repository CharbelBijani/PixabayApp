package com.dam.pixabayapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.MyViewHolder> {

    private Context context;
    private ArrayList<ModelItem> itemArrayList;

    public AdapterItem(Context context, ArrayList<ModelItem> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelItem currentItem = itemArrayList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String creator = currentItem.getCreator();
        int likes = currentItem.getLikes();

        holder.tvCreator.setText(creator);
        holder.tvLikes.setText("Likes : " + likes);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);

        Context context = holder.ivImageView.getContext();
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivImageView);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImageView;
        private TextView tvCreator, tvLikes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Le lien design et Java
            ivImageView = itemView.findViewById(R.id.ivImageView);
            tvCreator = itemView.findViewById(R.id.tvCreator);
            tvLikes = itemView.findViewById(R.id.tvLikes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }

    // La geston du click
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }
    // Constructeur qui sera appelé dans le MainActivity
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
