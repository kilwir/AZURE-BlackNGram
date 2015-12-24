package com.mopidev.blackngram.View;

import com.mopidev.blackngram.Model.UserImage;

import java.util.ArrayList;

/**
 * Bad Boys Team
 * Created by remyjallan on 18/12/2015.
 */
public interface FavoriteView {
    void showProgress();

    void hideProgress();

    void showPictures(ArrayList<UserImage> userImages);

    void setError();

    void initRecyclerView();
}
