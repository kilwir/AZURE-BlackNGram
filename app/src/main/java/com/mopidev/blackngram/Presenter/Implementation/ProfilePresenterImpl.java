package com.mopidev.blackngram.Presenter.Implementation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mopidev.blackngram.Listener.OnDeletePictureListener;
import com.mopidev.blackngram.Listener.OnItemClickListener;
import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.Presenter.ProfilePresenter;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.ProfileView;

import java.util.ArrayList;
import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 25/12/2015.
 */
public class ProfilePresenterImpl implements ProfilePresenter, OnLoadPicturesFinishedListener, OnDeletePictureListener {
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
    public void deletePicture(UserImage image,int position) {
        AppDataManager.getInstance().deletePicture(image, position, this);
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

    @Override
    public void onDeleteSuccess(UserImage image, int position) {
        mView.deletePictureFromRecyclerView(image, position);
    }

    @Override
    public void onDeleteError() {

    }
}
