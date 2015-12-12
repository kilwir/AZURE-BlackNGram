package com.mopidev.blackngram.View;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public interface LoginView {
    void showProgress();

    void hideProgress();

    void setError(String errorMessage);

    void navigateToHome();

}
