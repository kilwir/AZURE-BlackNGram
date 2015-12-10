package com.mopidev.blackngram.Listener;

/**
 * Bad Boys Team
 * Created by remyjallan on 10/12/2015.
 */
public interface OnCheckUserExistListener {
    void UserAlreadyExist();

    void UserDoesNotExist();

    void CheckUserExistError(String errorMessage);
}
