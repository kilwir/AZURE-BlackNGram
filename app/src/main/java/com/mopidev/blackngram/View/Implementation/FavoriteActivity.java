package com.mopidev.blackngram.View.Implementation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mopidev.blackngram.Presenter.FavoritePresenter;
import com.mopidev.blackngram.Presenter.Implementation.FavoritePresenterImpl;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.FavoriteView;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * Bad Boys Team
 * Created by remyjallan on 18/12/2015.
 */
public class FavoriteActivity extends AppCompatActivity implements FavoriteView {

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

        mPresenter = new FavoritePresenterImpl(this);

        mPresenter.loadPictures();

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
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setError() {
        Toast.makeText(getApplicationContext(), "Error load pictures", Toast.LENGTH_SHORT).show();
    }
}
