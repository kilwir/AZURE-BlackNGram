package com.mopidev.blackngram.View.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mopidev.blackngram.Adapter.PictureAdapter;
import com.mopidev.blackngram.Adapter.PicturePagerAdapter;
import com.mopidev.blackngram.Adapter.PictureProfileAdapter;
import com.mopidev.blackngram.Listener.OnItemProfileClickListener;
import com.mopidev.blackngram.Model.Constante;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.Presenter.Implementation.ProfilePresenterImpl;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.ProfileView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;
/**
 * Bad Boys Team
 * Created by remyjallan on 25/12/2015.
 */
public class ProfileActivity extends AppCompatActivity implements ProfileView, OnItemProfileClickListener {

    private static final String TAG = "ProfileActivity";

    private ProfilePresenterImpl mPresenter;

    @Bind(R.id.toolbar)
    public Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    @Bind(R.id.progressLoadPicture)
    public ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        Icepick.restoreInstanceState(this, savedInstanceState);

        setSupportActionBar(mToolbar);

        mPresenter = new ProfilePresenterImpl(this);

        this.initRecyclerView();

        mPresenter.loadPictures();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setError() {

    }

    @Override
    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showPictures(List<UserImage> userImages) {
        PictureProfileAdapter adapter = new PictureProfileAdapter(getApplicationContext(), userImages,this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void deletePictureFromRecyclerView(UserImage image, int position) {
        PictureProfileAdapter adapter = (PictureProfileAdapter) mRecyclerView.getAdapter();
        adapter.deleteItem(image, position);
    }

    @Override
    public void navigateToFullScreen(UserImage userImage, PicturePagerAdapter.StateImage currentImage) {
        Intent fullScreen = new Intent(this,FullScreenImageActivity.class);

        if(currentImage == PicturePagerAdapter.StateImage.BLACK_IMAGE){
            fullScreen.putExtra(Constante.NameExtraFullScreenImage, userImage.getBlackImageURL());
        }
        else
            fullScreen.putExtra(Constante.NameExtraFullScreenImage, userImage.getImageURL());

        startActivity(fullScreen);
    }

    @Override
    public void onItemClick(View view,UserImage userImage,PicturePagerAdapter.StateImage currentImage) {
        this.navigateToFullScreen(userImage,currentImage);
    }

    @Override
    public void onDeletePicture(UserImage image, int position) {
        mPresenter.deletePicture(image,position);
    }
}
