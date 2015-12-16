package com.mopidev.blackngram.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mopidev.blackngram.Listener.OnItemClickListener;
import com.mopidev.blackngram.Model.Picture;
import com.mopidev.blackngram.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
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
    public void onBindViewHolder(final PictureViewHolder holder, final int position) {
        final Picture picture = pictureList.get(position);
        String by = context.getString(R.string.by);
        holder.Name.setText(picture.getName());
        //holder.Image.setImageResource(R.drawable.nyc_black_and_white);
        holder.Author.setText(by + picture.getUserRowKey());
        Picasso.with(context)
                .load(picture.getBlackImageURL())
                .into(holder.Image);
        /*if(picture.Like) {
            holder.Like.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }*/

        if(this.itemClickListener != null)
            holder.setOnClickListener(this.itemClickListener);

        /*holder.Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picture. = !picture.Like;
                int newColor;

                if(picture.Like) {
                    newColor = context.getResources().getColor(R.color.colorAccent);
                } else {
                    newColor = context.getResources().getColor(R.color.cardview_light_background);
                }

                holder.Like.setBackgroundColor(newColor);

                holder.clickListener.onLikeItem(holder.Like,position);
            }
        });

        holder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.clickListener.onShareItem(holder.Share,position);
            }
        });*/
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
        protected Button Share;
        protected Button Like;
        protected OnItemClickListener clickListener;

        public PictureViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.pictureName);
            Image = (ImageView) itemView.findViewById(R.id.picture);
            Author  = (TextView) itemView.findViewById(R.id.pictureAuthor);
            Share = (Button) itemView.findViewById(R.id.share);
            Like = (Button) itemView.findViewById(R.id.like);
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
