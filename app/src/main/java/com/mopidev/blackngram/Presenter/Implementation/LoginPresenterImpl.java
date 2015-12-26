package com.mopidev.blackngram.Presenter.Implementation;

import android.content.Context;
import android.util.Log;

import com.mopidev.blackngram.Listener.OnLoginFinishedListener;
import com.mopidev.blackngram.Listener.OnSigninFinishedListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.ErrorCode;
import com.mopidev.blackngram.Model.User;
import com.mopidev.blackngram.Presenter.LoginPresenter;
import com.mopidev.blackngram.View.LoginView;

import hugo.weaving.DebugLog;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public class LoginPresenterImpl implements LoginPresenter,OnLoginFinishedListener,OnSigninFinishedListener {

    private static final String TAG = "LoginPresenterImpl";

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
    }

    @Override
    public void validateCredentials(String username,String password,Context context) {
        loginView.showProgress();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        AppDataManager.getInstance().login(user, context, this);
    }

    @Override
    public void validateSignin(String username, String password, Context context) {
        loginView.showProgress();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        AppDataManager.getInstance().signIn(user,context,this);
    }

    @Override
    public void checkCurrentUser(Context context) {
        AppDataManager.getInstance().getCurrentUser(context,this);
    }

    @Override
    public void onLoginError(int errorCode) {
        loginView.hideProgress();
        loginView.setError(ErrorCode.getMessage(errorCode));
    }

    @Override
    public void onLoginSuccess(User currentUser) {
        loginView.hideProgress();
        loginView.navigateToHome();
    }


    @Override
    public void onSigninError(int errorCode) {
        loginView.hideProgress();
        loginView.setError(ErrorCode.getMessage(errorCode));
    }

    @Override
    public void onSigninSuccess(User currentUser) {
        loginView.hideProgress();
        loginView.navigateToHome();
    }
}
