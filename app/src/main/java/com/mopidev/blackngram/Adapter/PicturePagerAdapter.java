package com.mopidev.blackngram.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mopidev.blackngram.Listener.OnItemProfileClickListener;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.R;
import com.squareup.picasso.Picasso;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/12/2015.
 */
public class PicturePagerAdapter extends PagerAdapter{

    private UserImage mUserImage;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private  OnItemProfileClickListener mListener;

    public PicturePagerAdapter(Context context,UserImage image,OnItemProfileClickListener listener){
        mUserImage = image;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListener = listener;
    }

    @Override
    public int getCount() {

        if(mUserImage == null)
            return 0;
        if(mUserImage.getImageURL() != null && mUserImage.getBlackImageURL() != null)
            return 2;
        else if (mUserImage.getImageURL() != null) //Si black null
            return 2;
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_picture_profile_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, mUserImage, StateImage.valueOf(position));
            }
        });

        String urlImage = null;

        if(position == StateImage.IMAGE.getValue())
            urlImage = mUserImage.getImageURL();
        else if(position == StateImage.BLACK_IMAGE.getValue()){
            if(mUserImage.getBlackImageURL() != null)
                urlImage = mUserImage.getBlackImageURL();
        }

        if(urlImage != null)
            Picasso.with(mContext).load(urlImage).into(imageView);
        else
            Picasso.with(mContext).load(R.drawable.in_progress).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public enum StateImage{
        IMAGE (0), BLACK_IMAGE(1);

        private final int value;

        StateImage(int value) {
            this.value = value;
        }

        static StateImage valueOf(int value){
            if(value == 0)
                return StateImage.IMAGE;
            else if(value == 1)
                return StateImage.BLACK_IMAGE;
            else
                return null;
        }

        public int getValue() {
            return value;
        }
    }
}
