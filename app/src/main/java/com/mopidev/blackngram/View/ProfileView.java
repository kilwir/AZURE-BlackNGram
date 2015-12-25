package com.mopidev.blackngram.View;

import com.mopidev.blackngram.Model.UserImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 25/12/2015.
 */
public interface ProfileView {
    void showProgress();

    void hideProgress();

    void setError();

    void initRecyclerView();

    void showPictures(List<UserImage> userImages);
}
