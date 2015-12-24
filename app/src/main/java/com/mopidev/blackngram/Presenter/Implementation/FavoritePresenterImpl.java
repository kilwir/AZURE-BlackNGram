package com.mopidev.blackngram.Presenter.Implementation;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.Presenter.FavoritePresenter;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.FavoriteView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Bad Boys Team
 * Created by remyjallan on 18/12/2015.
 */
public class FavoritePresenterImpl implements FavoritePresenter {

    private static final String TAG = "FavoritePresenterImpl";

    private FavoriteView mView;

    private ArrayList<UserImage> mUserImages;

    public FavoritePresenterImpl(FavoriteView view){
        mView = view;
        mUserImages = new ArrayList<>();
    }

    @Override
    public void loadPictures(Intent intent) {
        mView.showProgress();
        mUserImages = intent.getParcelableArrayListExtra("favoriteImage");
        Log.d(TAG,"LoadPictures");
    }
}
