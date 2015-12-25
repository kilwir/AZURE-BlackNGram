package com.mopidev.blackngram.Presenter.Implementation;

import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.Presenter.ProfilePresenter;
import com.mopidev.blackngram.View.ProfileView;

import java.util.ArrayList;
import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 25/12/2015.
 */
public class ProfilePresenterImpl implements ProfilePresenter, OnLoadPicturesFinishedListener {
    private ProfileView mView;

    public ProfilePresenterImpl(ProfileView view){
        mView = view;
    }

    @Override
    public void loadPictures() {
        mView.showProgress();
        AppDataManager.getInstance().loadUserPictures(this);
    }

    @Override
    public void onSuccess(List<UserImage> userImages) {
        mView.hideProgress();
        mView.showPictures(userImages);
    }

    @Override
    public void onError() {
        mView.hideProgress();
        mView.setError();
    }
}
