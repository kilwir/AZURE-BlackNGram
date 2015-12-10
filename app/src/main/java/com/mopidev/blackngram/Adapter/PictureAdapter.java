package com.mopidev.blackngram.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mopidev.blackngram.Listener.OnItemClickListener;
import com.mopidev.blackngram.Model.Picture;
import com.mopidev.blackngram.R;

import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private static final String TAG = "PictureAdapter";

    private List<Picture> pictureList;
    private Context context;
    private OnItemClickListener itemClickListener;

    public PictureAdapter(Context context,List<Picture> pictureList){
        this.pictureList = pictureList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
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

        if(this.itemClickListener != null)
            holder.setOnClickListener(this.itemClickListener);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {

        protected TextView  Name;
        protected ImageView Image;
        protected TextView  Author;
        protected OnItemClickListener clickListener;

        public PictureViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.pictureName);
            Image = (ImageView) itemView.findViewById(R.id.picture);
            Author  = (TextView) itemView.findViewById(R.id.pictureAuthor);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setOnClickListener(OnItemClickListener clickListener){
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(view, getPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(view, getPosition());
            return true;
        }
    }
}
