package com.mopidev.blackngram.Presenter.Implementation;

import android.content.Context;
import android.util.Log;

import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.Picture;
import com.mopidev.blackngram.Presenter.MainPresenter;
import com.mopidev.blackngram.View.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public class MainPresenterImpl implements MainPresenter,OnLoadPicturesFinishedListener {

    private static final String TAG = "MainPresenterImpl";

    private MainView mainView;

    private List<Picture> mPictureList;

    public MainPresenterImpl(MainView view){
        this.mainView = view;
        mPictureList = new ArrayList<>();
    }

    @Override
    public void loadPictures(Context context) {
        mainView.showProgress();
        AppDataManager.getInstance().loadAllPictures(context,this);
    }

    @Override
    public void pictureClick(int position) {
        mainView.navigateToFullScreen(mPictureList.get(position));
    }

    @Override
    public void onSuccess(List<Picture> pictures) {
        mainView.hideProgress();
        mPictureList = pictures;
        mainView.showPicturesList(pictures);
    }

    @Override
    public void onError() {
        mainView.hideProgress();
        mainView.SetError();
        Log.d(TAG,"onError");
    }
}
