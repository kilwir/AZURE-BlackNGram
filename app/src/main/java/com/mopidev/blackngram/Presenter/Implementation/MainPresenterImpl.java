package com.mopidev.blackngram.Presenter.Implementation;

import android.content.Context;
import android.util.Log;

import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.Picture;
import com.mopidev.blackngram.Presenter.MainPresenter;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public class MainPresenterImpl implements MainPresenter,OnLoadPicturesFinishedListener {

    private static final String TAG = "MainPresenterImpl";

    private MainView mMainView;
    private Context mContext;

    private List<Picture> mPictureList;

    public MainPresenterImpl(MainView view,Context context){
        this.mMainView = view;
        mContext = context;
        mPictureList = new ArrayList<>();
    }

    @Override
    public void loadPictures() {
        mMainView.showProgress();
        AppDataManager.getInstance().loadAllPictures(mContext,this);
    }

    @Override
    public void pictureClick(int position) {
        mMainView.navigateToFullScreen(mPictureList.get(position));
    }

    @Override
    public void optionSelected(int id) {
        switch (id) {
            case R.id.logout:
                AppDataManager.getInstance().logout(mContext);
                mMainView.navigateToLogin();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(List<Picture> pictures) {
        mMainView.hideProgress();
        mPictureList = pictures;
        mMainView.showPicturesList(pictures);
    }

    @Override
    public void onError() {
        mMainView.hideProgress();
        mMainView.setError();
        Log.d(TAG,"onError");
    }
}
