package com.mopidev.blackngram.Listener;

import com.mopidev.blackngram.Model.Picture;

import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public interface OnLoadPicturesFinishedListener {
    void onSuccess(List<Picture> pictures);
    void onError();
}
