package com.mopidev.blackngram.View.Implementation;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mopidev.blackngram.Presenter.Implementation.LoginPresenterImpl;
import com.mopidev.blackngram.Presenter.LoginPresenter;
import com.mopidev.blackngram.R;
import com.mopidev.blackngram.View.LoginView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    public static final String TAG = "LoginActivity";

    @Bind(R.id.progressLogin)
    public ProgressBar progressBar;

    @Bind(R.id.username)
    public EditText username;

    @Bind(R.id.password)
    public EditText password;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Icepick.restoreInstanceState(this, savedInstanceState);

        presenter = new LoginPresenterImpl(this);

        presenter.checkCurrentUser(getApplicationContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setError(String errorMessage) {
        username.setError(errorMessage);
    }

    @Override
    public void navigateToHome() {
        Log.d(TAG,"Navigate to home");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.loginButton)
    public void loginButton(){
        Log.d(TAG, "LoginButton Pressed");
        this.presenter.validateCredentials(this.username.getText().toString(), this.password.getText().toString(), getApplicationContext());
    }

    @OnClick(R.id.signInButton)
    public void signInButton() {
        presenter.validateSignin(this.username.getText().toString(),this.password.getText().toString(),getApplicationContext());
    }
}
