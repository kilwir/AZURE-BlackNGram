package com.mopidev.blackngram.Presenter.Implementation;

import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.mopidev.blackngram.Presenter.FavoritePresenter;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.FavoriteView;

import butterknife.Bind;

/**
 * Bad Boys Team
 * Created by remyjallan on 18/12/2015.
 */
public class FavoritePresenterImpl implements FavoritePresenter {

    private FavoriteView mView;

    public FavoritePresenterImpl(FavoriteView view){
        mView = view;
    }

    @Override
    public void loadPictures() {
        mView.showProgress();
    }
}
