package com.mopidev.blackngram.Listener;

import com.mopidev.blackngram.Model.UserImage;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/12/2015.
 */
public interface OnDeletePictureListener {

    void onDeleteSuccess(UserImage image,int position);

    void onDeleteError();

}
