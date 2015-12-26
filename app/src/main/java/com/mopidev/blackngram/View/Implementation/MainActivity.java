package com.mopidev.blackngram.View.Implementation;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mopidev.blackngram.Adapter.PictureAdapter;
import com.mopidev.blackngram.Model.Constante;
import com.mopidev.blackngram.Model.UserImage;
import com.mopidev.blackngram.Presenter.Implementation.MainPresenterImpl;
import com.mopidev.blackngram.Presenter.MainPresenter;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.MainView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_PICK = 2;

    @Bind(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout mSwipeToRefresh;

    @Bind(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    @Bind(R.id.progressLoadPicture)
    public ProgressBar mProgressLoadPicture;

    @Bind(R.id.toolbar)
    public Toolbar mToolbar;

    @Bind(R.id.fab_all)
    public FloatingActionsMenu mFloatingActionsMenu;

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
    public void setError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToFullScreen(UserImage userImage) {
        Intent fullScreen = new Intent(this,FullScreenImageActivity.class);
        fullScreen.putExtra(Constante.NameExtraFullScreenImage, userImage.getBlackImageURL());
        startActivity(fullScreen);
    }

    @Override
    public void navigateToLogin() {
        Intent loginView = new Intent(this,LoginActivity.class);
        loginView.putExtra(Constante.NameExtraLoginDisconnect,true);
        startActivity(loginView);
        finish();
    }

    @Override
    public void navigateToFavorite(ArrayList<UserImage> favoriteImage) {
        Intent favoriteView = new Intent(this,FavoriteActivity.class);
        favoriteView.putParcelableArrayListExtra(Constante.NameExtraFavoriteImages,favoriteImage);
        startActivity(favoriteView);
    }

    @OnClick(R.id.fab_picture)
    @Override
    public void navigateToCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @OnClick(R.id.fab_picker)
    @Override
    public void navigateToPickPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), REQUEST_IMAGE_PICK);
        }
    }

    @Override
    public void navigateToProfile() {
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showPictures(List<UserImage> userImageList) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap imageBitmap = null;

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                imageStream = getContentResolver().openInputStream(selectedImage);
                imageBitmap = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                this.setError("Error upload image");
            }
        }

        if(imageBitmap != null) {
            presenter.addPicture(imageBitmap);
        }

        mFloatingActionsMenu.collapse();
    }
}
