package com.mopidev.blackngram.Presenter;

import com.mopidev.blackngram.Model.UserImage;

/**
 * Bad Boys Team
 * Created by remyjallan on 25/12/2015.
 */
public interface ProfilePresenter {
    void loadPictures();

    void deletePicture(UserImage image,int position);
}
