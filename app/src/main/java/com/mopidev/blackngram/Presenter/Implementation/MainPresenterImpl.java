package com.mopidev.blackngram.Presenter.Implementation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.mopidev.blackngram.Listener.OnImageUploadedListener;
import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.Presenter.MainPresenter;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.MainView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */
public class MainPresenterImpl implements MainPresenter,OnLoadPicturesFinishedListener, OnImageUploadedListener {

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
    public void loadPictures(Boolean isSwipeToRefresh) {
        if( !isSwipeToRefresh)
            mMainView.showProgress();
        AppDataManager.getInstance().loadAllPictures(this);
    }

    @Override
    public void likePicture(UserImage image) {
        if(!image.IsFavorite){
            AppDataManager.getInstance().addFavorite(image);
        } else {
            AppDataManager.getInstance().deleteFavorite(image);
        }
    }

    @Override
    public void pictureClick(int position) {
        mMainView.navigateToFullScreen(mUserImageList.get(position));
    }

    @Override
    public void addPicture(Bitmap image) {
        AppDataManager.getInstance().uploadPicture(image,this);
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
                mMainView.navigateToFavorite(this.prepareNavigateToFavorite());
                break;
            default:
                break;
        }
    }

    private ArrayList<UserImage> prepareNavigateToFavorite(){
        ArrayList<UserImage> list = new ArrayList<>();

        for( UserImage userImage :mUserImageList){
            if(userImage.IsFavorite)
                list.add(userImage);
        }

        return list;
    }

    @Override
    public void onSuccess(List<UserImage> userImages) {
        mMainView.hideProgress();
        mUserImageList = userImages;
        mMainView.showPictures(userImages);
    }

    @Override
    public void onError() {
        mMainView.hideProgress();
        mMainView.setError("On error as occurred");
        Log.d(TAG,"onError");
    }

    @Override
    public void onImageUploaded() {
        Log.d(TAG,"onImageUploaded");
    }

    @Override
    public void onImageUploadError() {
        mMainView.setError("Error upload image");
    }
}
