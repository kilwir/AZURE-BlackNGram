package com.mopidev.blackngram.View;

import com.mopidev.blackngram.Listener.OnItemClickListener;
import com.mopidev.blackngram.Model.UserImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public interface MainView extends OnItemClickListener{
    void initRecyclerView();

    void showProgress();

    void hideProgress();

    void setError(String message);

    void navigateToFullScreen(UserImage userImage);

    void navigateToLogin();

    void navigateToFavorite(ArrayList<UserImage> favoriteImage);

    void navigateToCamera();

    void navigateToPickPicture();

    void showPictures(List<UserImage> userImageList);
}
