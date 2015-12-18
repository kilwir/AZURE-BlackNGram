package com.mopidev.blackngram.Listener;

import com.mopidev.blackngram.Model.UserImage;

import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public interface OnLoadPicturesFinishedListener {
    void onSuccess(List<UserImage> userImages);
    void onError();
}
