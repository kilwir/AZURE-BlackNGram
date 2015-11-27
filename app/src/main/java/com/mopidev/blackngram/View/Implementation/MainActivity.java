package com.mopidev.blackngram.View.Implementation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.mopidev.blackngram.R;

import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import icepick.Icepick;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Icepick.restoreInstanceState(this,savedInstanceState);
    }

    @Override

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this,outState);
    }
}
