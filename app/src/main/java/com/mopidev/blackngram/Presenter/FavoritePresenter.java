package com.mopidev.blackngram.Presenter;

import android.content.Context;
import android.content.Intent;

import com.mopidev.blackngram.Model.UserImage;

/**
 * Bad Boys Team
 * Created by remyjallan on 18/12/2015.
 */
public interface FavoritePresenter {
    void loadPictures(Intent intent);

    void deleteFavorite(Context context,UserImage userImage);
}
