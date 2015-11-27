package com.mopidev.blackngram.Presenter.Implementation;

import android.content.Context;
import android.util.Log;

import com.mopidev.blackngram.Listener.OnLoginFinishedListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.User;
import com.mopidev.blackngram.Presenter.LoginPresenter;
import com.mopidev.blackngram.View.LoginView;

import hugo.weaving.DebugLog;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public class LoginPresenterImpl implements LoginPresenter,OnLoginFinishedListener {

    private static final String TAG = "LoginPresenterImpl";

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
    }

    @Override
    public void validateCredentials(String username,Context context) {
        loginView.showProgress();
        AppDataManager.getInstance().login(username, context, this);
    }

    @Override
    public void checkCurrentUser(Context context) {
        AppDataManager.getInstance().getCurrentUser(context,this);
    }

    @Override
    public void onError() {
        Log.d(TAG,"onError");
        loginView.hideProgress();
        loginView.SetError();
    }

    @Override
    @DebugLog
    public void onSuccess(User currentUser) {
        Log.d(TAG,"onSuccess");
        loginView.hideProgress();
        loginView.navigateToHome();
    }


}
