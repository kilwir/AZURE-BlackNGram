package com.mopidev.blackngram.Listener;

import com.mopidev.blackngram.Model.User;

/**
 * Bad Boys Team
 * Created by remyjallan on 17/12/2015.
 */
public interface OnLoadUserListener {
    void OnSuccess(User user);
    void OnError();
}
