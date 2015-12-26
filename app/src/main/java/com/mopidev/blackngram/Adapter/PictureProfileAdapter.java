package com.mopidev.blackngram.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mopidev.blackngram.Listener.OnItemProfileClickListener;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.R;

import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/12/2015.
 */
public class PictureProfileAdapter extends RecyclerView.Adapter<PictureProfileAdapter.PictureProfileViewHolder>{

    private List<UserImage> mUserImages;
    private Context mContext;
    private OnItemProfileClickListener mListener;

    public PictureProfileAdapter(Context context,List<UserImage> userImageList,OnItemProfileClickListener listener){
        this.mUserImages = userImageList;
        this.mContext = context;
        this.mListener = listener;
    }

    public void deleteItem(UserImage userImage,int position){
        mUserImages.remove(userImage);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mUserImages.size());
    }


    @Override
    public PictureProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_picture_profile,parent,false);

        return new PictureProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PictureProfileViewHolder holder,int position) {
        UserImage userImage = mUserImages.get(position);

        PicturePagerAdapter picturePagerAdapter = new PicturePagerAdapter(mContext,userImage,mListener);
        holder.mPagerAdapter.setAdapter(picturePagerAdapter);

        //for delete action
        holder.mDelete.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mUserImages.size();
    }

    class PictureProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ViewPager mPagerAdapter;
        protected ImageButton mDelete;

        public PictureProfileViewHolder(View itemView) {
            super(itemView);
            mPagerAdapter = (ViewPager)itemView.findViewById(R.id.image_pager);
            mDelete = (ImageButton) itemView.findViewById(R.id.delete_image);
            mDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onDeletePicture(mUserImages.get((Integer)v.getTag()),(Integer)v.getTag());
        }
    }
}
