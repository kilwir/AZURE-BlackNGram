package com.mopidev.blackngram.Presenter.Implementation;

import com.mopidev.blackngram.Presenter.FavoritePresenter;
import com.mopidev.blackngram.View.FavoriteView;

/**
 * Bad Boys Team
 * Created by remyjallan on 18/12/2015.
 */
public class FavoritePresenterImpl implements FavoritePresenter {

    private FavoriteView mView;

    public FavoritePresenterImpl(FavoriteView view){
        mView = view;
    }
}
