package com.mopidev.blackngram.Presenter;

import android.content.Context;

import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public interface MainPresenter {
    void loadPictures(Context context);

    void pictureClick(int position);
}
