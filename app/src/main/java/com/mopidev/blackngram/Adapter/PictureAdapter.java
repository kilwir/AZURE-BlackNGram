package com.mopidev.blackngram.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mopidev.blackngram.Model.Picture;
import com.mopidev.blackngram.R;

import java.util.List;

import butterknife.BindDrawable;
import hugo.weaving.DebugLog;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private List<Picture> pictureList;
    private Context context;

    public PictureAdapter(List<Picture> pictureList,Context context){
        this.pictureList = pictureList;
        this.context = context;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_picture,parent,false);

        return new PictureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        Picture picture = pictureList.get(position);
        String by = context.getString(R.string.by);
        holder.Name.setText(picture.Name);
        holder.Image.setImageResource(R.drawable.nyc_black_and_white);
        holder.Author.setText(by + picture.UserOwner);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {

        protected TextView  Name;
        protected ImageView Image;
        protected TextView  Author;

        public PictureViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.pictureName);
            Image = (ImageView) itemView.findViewById(R.id.picture);
            Author  = (TextView) itemView.findViewById(R.id.pictureAuthor);
        }

    }
}
