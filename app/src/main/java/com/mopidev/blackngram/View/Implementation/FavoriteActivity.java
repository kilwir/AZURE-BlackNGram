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
import android.widget.Toast;

import com.mopidev.blackngram.Adapter.PictureAdapter;
import com.mopidev.blackngram.Listener.OnItemClickListener;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.Presenter.FavoritePresenter;
import com.mopidev.blackngram.Presenter.Implementation.FavoritePresenterImpl;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.FavoriteView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * Bad Boys Team
 * Created by remyjallan on 18/12/2015.
 */
public class FavoriteActivity extends AppCompatActivity implements FavoriteView, OnItemClickListener {

    private static final String TAG = "FavoriteActivity";

    @Bind(R.id.toolbar)
    public Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    @Bind(R.id.progressLoadPicture)
    public ProgressBar mProgressBar;

    private FavoritePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this);

        Icepick.restoreInstanceState(this, savedInstanceState);

        setSupportActionBar(mToolbar);

        this.initRecyclerView();

        mPresenter = new FavoritePresenterImpl(this);

        mPresenter.loadPictures(getIntent());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Icepick.saveInstanceState(this,outState);
    }

    @Override
    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void navigateToFullScreen(UserImage userImage) {
        Intent fullScreen = new Intent(this,FullScreenImageActivity.class);
        fullScreen.putExtra("PictureGetBlackImageURL", userImage.getBlackImageURL());
        startActivity(fullScreen);
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
    public void showPictures(ArrayList<UserImage> userImages) {
        Log.d(TAG,String.valueOf(userImages.size()));
        PictureAdapter adapter = new PictureAdapter(getApplicationContext(), userImages);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setError() {
        Toast.makeText(getApplicationContext(), "Error load pictures", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        mPresenter.pictureClick(position);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onLikeItem(View view, UserImage image) {
        mPresenter.deleteFavorite(image);
    }
}
