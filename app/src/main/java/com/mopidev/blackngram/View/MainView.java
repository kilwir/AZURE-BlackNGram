package com.mopidev.blackngram.View;

import com.mopidev.blackngram.Listener.OnItemClickListener;
import com.mopidev.blackngram.Model.UserImage;

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

    void navigateToFullScreen(UserImage userImage);

    void navigateToLogin();

    void navigateToFavorite();

    void showPicturesList(List<UserImage> userImageList);
}
