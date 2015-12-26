package com.mopidev.blackngram.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mopidev.blackngram.Listener.OnItemClickListener;
import com.mopidev.blackngram.Listener.OnLoadUserListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.Model.User;
import com.mopidev.blackngram.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private static final String TAG = "PictureAdapter";

    private List<UserImage> mUserImageList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public PictureAdapter(Context context,List<UserImage> userImageList){
        this.mUserImageList = userImageList;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClickListener = listener;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_picture,parent,false);

        return new PictureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PictureViewHolder holder, final int position) {
        final UserImage userImage = mUserImageList.get(position);
        final String by = mContext.getString(R.string.by);
        final String anonymous = mContext.getString(R.string.anonymous);

        Picasso.with(mContext)
                .load(userImage.getBlackThumbnailURL())
                .error(R.drawable.error_loading)
                .into(holder.Image);

        AppDataManager.getInstance().getUserById(userImage.getUserRowKey(), new OnLoadUserListener() {
            @Override
            public void OnSuccess(User user) {
                holder.Author.setText(by + user.getUsername());
            }

            @Override
            public void OnError() {
                holder.Author.setText(by + anonymous);
            }
        });

        if(userImage.IsFavorite) {
            holder.Like.setBackgroundResource(R.drawable.ic_heart_red);
        } else {
            holder.Like.setBackgroundResource(R.drawable.ic_empty_heart);
        }

        if(this.mItemClickListener != null) {
            holder.setOnClickListener(this.mItemClickListener);

            holder.Like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newIcon;

                    holder.clickListener.onLikeItem(holder.Like, userImage);

                    if (userImage.IsFavorite){
                        newIcon = R.drawable.ic_empty_heart;
                        userImage.IsFavorite = false;
                    } else {
                        newIcon = R.drawable.ic_heart_red;
                        userImage.IsFavorite = true;
                    }

                    holder.Like.setBackgroundResource(newIcon);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mUserImageList.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        protected ImageView Image;
        protected TextView  Author;
        protected ImageButton Like;
        protected OnItemClickListener clickListener;

        public PictureViewHolder(View itemView) {
            super(itemView);
            Image = (ImageView) itemView.findViewById(R.id.picture);
            Author  = (TextView) itemView.findViewById(R.id.pictureAuthor);
            Like = (ImageButton) itemView.findViewById(R.id.like);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void setOnClickListener(OnItemClickListener clickListener){
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (clickListener != null)
                clickListener.onItemLongClick(view, getAdapterPosition());
            return true;
        }
    }
}
