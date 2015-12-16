package com.mopidev.blackngram.Presenter.Implementation;

import android.content.Context;
import android.util.Log;

import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.Picture;
import com.mopidev.blackngram.Presenter.MainPresenter;
import com.mopidev.blackngram.View.MainView;

import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public class MainPresenterImpl implements MainPresenter,OnLoadPicturesFinishedListener {

    private static final String TAG = "MainPresenterImpl";

    private MainView mainView;

    public MainPresenterImpl(MainView view){
        this.mainView = view;
    }

    @Override
    public void loadPictures(Context context) {
        mainView.showProgress();
        AppDataManager.getInstance().loadAllPictures(context,this);
    }

    @Override
    public void onSuccess(List<Picture> pictures) {
        mainView.hideProgress();
        mainView.showPicturesList(pictures);
    }

    @Override
    public void onError() {
        mainView.hideProgress();
        mainView.SetError();
        Log.d(TAG,"onError");
    }
}
