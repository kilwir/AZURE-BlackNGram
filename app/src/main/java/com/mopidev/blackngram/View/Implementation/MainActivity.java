package com.mopidev.blackngram.View.Implementation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mopidev.blackngram.Adapter.PictureAdapter;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.Presenter.Implementation.MainPresenterImpl;
import com.mopidev.blackngram.Presenter.MainPresenter;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.MainView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "MainActivity";

    @Bind(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout mSwipeToRefresh;

    @Bind(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    @Bind(R.id.progressLoadPicture)
    public ProgressBar mProgressLoadPicture;

    @Bind(R.id.toolbar)
    public Toolbar mToolbar;

    private MainPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Icepick.restoreInstanceState(this, savedInstanceState);

        initRecyclerView();

        setSupportActionBar(mToolbar);

        mSwipeToRefresh.setOnRefreshListener(this);

        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);

        mSwipeToRefresh.setColorSchemeColors(color);

        presenter = new MainPresenterImpl(this,this);

        presenter.loadPictures(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        presenter.optionSelected(item.getItemId());

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showProgress() {
        mProgressLoadPicture.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressLoadPicture.setVisibility(View.GONE);
        mSwipeToRefresh.setRefreshing(false);
    }

    @Override
    public void setError() {
        Toast.makeText(getApplicationContext(),"Error load pictures",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToFullScreen(UserImage userImage) {
        Intent fullScreen = new Intent(this,FullScreenImageActivity.class);
        fullScreen.putExtra("PictureGetBlackImageURL", userImage.getBlackImageURL());
        startActivity(fullScreen);
    }

    @Override
    public void navigateToLogin() {
        Intent loginView = new Intent(this,LoginActivity.class);
        startActivity(loginView);
        finish();
    }

    @Override
    public void navigateToFavorite() {
        Intent favoriteView = new Intent(this,FavoriteActivity.class);
        startActivity(favoriteView);
    }

    @Override
    public void showPicturesList(List<UserImage> userImageList) {
        PictureAdapter adapter = new PictureAdapter(getApplicationContext(), userImageList);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.pictureClick(position);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "Item Long Click : " + position);
    }

    @Override
    public void onLikeItem(View view, UserImage image) {
        presenter.likePicture(image);
    }

    @Override
    public void onRefresh() {
        presenter.loadPictures(true);
    }

    @OnClick(R.id.fab_add)
    public void addPicture(View view){
        Log.d(TAG,"Add picture");
    }
}
