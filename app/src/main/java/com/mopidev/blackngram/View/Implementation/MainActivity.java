package com.mopidev.blackngram.View.Implementation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mopidev.blackngram.Adapter.PictureAdapter;
import com.mopidev.blackngram.Model.AppDataManager;
import com.mopidev.blackngram.Model.Picture;
import com.mopidev.blackngram.Presenter.Implementation.MainPresenterImpl;
import com.mopidev.blackngram.Presenter.MainPresenter;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.MainView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import icepick.Icepick;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public class MainActivity extends AppCompatActivity implements MainView {

    public static final String TAG = "MainActivity";

    @Bind(R.id.recyclerView)
    public RecyclerView recyclerView;

    @Bind(R.id.progressLoadPicture)
    public ProgressBar progressLoadPicture;

    private MainPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Icepick.restoreInstanceState(this, savedInstanceState);

        presenter = new MainPresenterImpl(this);

        initRecyclerView();

        presenter.loadPictures();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showProgress() {
        progressLoadPicture.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressLoadPicture.setVisibility(View.GONE);
    }

    @Override
    public void SetError() {
        Toast.makeText(getApplicationContext(),"Error load pictures",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPicturesList(List<Picture> pictureList) {
        PictureAdapter adapter = new PictureAdapter(pictureList,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

}
