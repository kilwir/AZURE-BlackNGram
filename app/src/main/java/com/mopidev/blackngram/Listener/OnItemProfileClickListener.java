package com.mopidev.blackngram.Listener;

import android.view.View;

import com.mopidev.blackngram.Adapter.PicturePagerAdapter;
import com.mopidev.blackngram.Model.UserImage;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/12/2015.
 */

public interface OnItemProfileClickListener {
    void onItemClick(View view,UserImage userImage,PicturePagerAdapter.StateImage currentImage);
}
