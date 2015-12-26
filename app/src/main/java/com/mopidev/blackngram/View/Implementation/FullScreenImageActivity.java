package com.mopidev.blackngram.View.Implementation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mopidev.blackngram.Model.Constante;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.FullScreenImageView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * Bad Boys Team
 * Created by remyjallan on 16/12/2015.
 */
public class FullScreenImageActivity extends AppCompatActivity implements FullScreenImageView {

    @Bind(R.id.imageFullScreen)
    ImageView mImageFullScreen;

    private String mPictureGetBlackImageURL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        this.hideUI();

        ButterKnife.bind(this);

        Icepick.restoreInstanceState(this, savedInstanceState);

        mPictureGetBlackImageURL = getIntent().getStringExtra(Constante.NameExtraFullScreenImage);

        if(mPictureGetBlackImageURL != null)
            Picasso.with(this).load(mPictureGetBlackImageURL).into(mImageFullScreen);
        else
            Picasso.with(this).load(R.drawable.in_progress).into(mImageFullScreen);
    }

    private void hideUI() {
        int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        getWindow().getDecorView().setSystemUiVisibility(mUIFlag);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Icepick.saveInstanceState(this,outState);
    }
}
