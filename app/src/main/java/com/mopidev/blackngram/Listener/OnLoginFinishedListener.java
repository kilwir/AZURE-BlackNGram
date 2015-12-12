package com.mopidev.blackngram.Listener;

import com.mopidev.blackngram.Model.User;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public interface OnLoginFinishedListener {

    public void onLoginError(int errorCode);

    public void onLoginSuccess(User currentUser);
}
