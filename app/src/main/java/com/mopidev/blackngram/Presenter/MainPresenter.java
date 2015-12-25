package com.mopidev.blackngram.Presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Model.UserImage;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public interface MainPresenter {
    void loadPictures(Boolean isSwipeToRefresh);

    void likePicture(UserImage image);

    void pictureClick(int position);

    void addPicture(Bitmap image);

    void optionSelected(int id);
}
