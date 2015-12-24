package com.mopidev.blackngram.View.Implementation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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

    private FavoritePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this);

        Icepick.restoreInstanceState(this, savedInstanceState);

        setSupportActionBar(mToolbar);

        mPresenter = new FavoritePresenterImpl(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Icepick.saveInstanceState(this,outState);
    }
}
