package com.walton.hoteltv;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.walton.hoteltv.model.hotel.Gallery;

import java.util.List;

public class PhotoRecycler extends RecyclerView.Adapter<PhotoRecycler.ViewHolder> {
    List<Gallery> photoList;
    Context context;
    PhotoRecycler(List<Gallery> photoList,Context context){
        this.photoList = photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gallery gallery = photoList.get(position);
        //Log.d("picImg","https://hotel-dera.waltontvrni.com/public/files/"+gallery.getFilenames().replace("\"",""));
                Glide
                .with(context)
                .load("https://hotel-dera.waltontvrni.com/public/files/"+gallery.getFilenames().replace("\"",""))
                .centerCrop()
                .placeholder(R.drawable.comming_soon)
                .into(holder.imageView);
//            Picasso.get()
//                    .load("https://hotel-dera.waltontvrni.com/public/files/"+gallery.getFilenames().replace("\"",""))
//                    .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}
