package com.mopidev.blackngram.Listener;

import com.mopidev.blackngram.Model.User;

/**
 * Bad Boys Team
 * Created by remyjallan on 10/12/2015.
 */
public interface OnSigninFinishedListener {
    public void onSigninError();

    public void onSigninSuccess(User currentUser);
}
