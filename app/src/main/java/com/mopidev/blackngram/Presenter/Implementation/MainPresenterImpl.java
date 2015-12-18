package com.mopidev.blackngram.Presenter.Implementation;

import android.content.Context;
import android.util.Log;

import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.UserImage;
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

    private List<UserImage> mUserImageList;

    public MainPresenterImpl(MainView view,Context context){
        this.mMainView = view;
        mContext = context;
        mUserImageList = new ArrayList<>();
    }

    @Override
    public void loadPictures() {
        mMainView.showProgress();
        AppDataManager.getInstance().loadAllPictures(mContext,this);
    }

    @Override
    public void likePicture(UserImage image) {
        if(!image.IsFavorite){
            AppDataManager.getInstance().addFavorite(image,mContext);
        } else {
            AppDataManager.getInstance().deleteFavorite(image,mContext);
        }
    }

    @Override
    public void pictureClick(int position) {
        mMainView.navigateToFullScreen(mUserImageList.get(position));
    }

    @Override
    public void optionSelected(int id) {
        switch (id) {
            case R.id.logout:
                AppDataManager.getInstance().logout(mContext);
                mMainView.navigateToLogin();
                break;
            case R.id.favorite:
                Log.d(TAG,"FAVORITE VIEW");
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(List<UserImage> userImages) {
        mMainView.hideProgress();
        mUserImageList = userImages;
        mMainView.showPicturesList(userImages);
    }

    @Override
    public void onError() {
        mMainView.hideProgress();
        mMainView.setError();
        Log.d(TAG,"onError");
    }
}
