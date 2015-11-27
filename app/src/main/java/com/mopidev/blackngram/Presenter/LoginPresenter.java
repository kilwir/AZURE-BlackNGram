package com.mopidev.blackngram.Presenter;

import android.content.Context;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public interface LoginPresenter {
    void validateCredentials(String username,Context context);
    void checkCurrentUser(Context context);
}
