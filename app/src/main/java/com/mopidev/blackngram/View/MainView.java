package com.mopidev.blackngram.View;

import com.mopidev.blackngram.Listener.OnItemClickListener;
import com.mopidev.blackngram.Model.Picture;

import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public interface MainView extends OnItemClickListener{
    void initRecyclerView();

    void showProgress();

    void hideProgress();

    void setError();

    void navigateToFullScreen(Picture picture);

    void navigateToLogin();

    void showPicturesList(List<Picture> pictureList);
}
