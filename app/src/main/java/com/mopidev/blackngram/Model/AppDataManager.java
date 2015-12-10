package com.mopidev.blackngram.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageUri;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Listener.OnLoginFinishedListener;
import com.mopidev.blackngram.Presenter.LoginPresenter;

import java.net.ContentHandler;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public class AppDataManager {

    private static final String TAG = "AppDataManager";

    private static final String PREFERENCE_NAME = "PREFERENCE_BLACKNGRAM";
    private static final String PREFERENCE_NAME_USERNAME = PREFERENCE_NAME + "_USER";
    private static final String PREFERENCE_NAME_PASSWORD = PREFERENCE_NAME + "_PASSWORD";

    private static AppDataManager ourInstance = new AppDataManager();

    public static AppDataManager getInstance() {
        return ourInstance;
    }

    private AppDataManager() {}

    public void login(final String username, final Context context,final OnLoginFinishedListener loginFinishedListener) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                if(username.equals("admin")){
                    User currentUser = new User();
                    currentUser.Username = username;
                    currentUser.Password = "";
                    saveCurrentUser(currentUser,context);
                    loginFinishedListener.onSuccess(currentUser);
                }
                else
                    loginFinishedListener.onError();
            }
        }, 1000);
    }

    public void logout(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.remove(PREFERENCE_NAME_USERNAME);
        editor.remove(PREFERENCE_NAME_PASSWORD);

        editor.apply();
    }

    private void saveCurrentUser(User user,Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(PREFERENCE_NAME_USERNAME,user.Username);
        editor.putString(PREFERENCE_NAME_PASSWORD,user.Password);

        editor.apply();
    }

    public void getCurrentUser(Context context,OnLoginFinishedListener listener){
        User currentUser = new User();
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);

        currentUser.Username = settings.getString(PREFERENCE_NAME_USERNAME,null);
        currentUser.Password = settings.getString(PREFERENCE_NAME_PASSWORD,"");

        if( currentUser.Username != null )
            listener.onSuccess(currentUser);

    }

    public User getCurrentUser(Context context) {
        User currentUser = new User();
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);

        currentUser.Username = settings.getString(PREFERENCE_NAME_USERNAME,null);
        currentUser.Password = settings.getString(PREFERENCE_NAME_PASSWORD,"");

        if( currentUser.Username != null )
            return currentUser;
        else
            return null;
    }

    public void loadPictures(final OnLoadPicturesFinishedListener listener){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                List<Picture> pictureList = new ArrayList<Picture>();

                for(int i = 0;i < 10; i++) {
                    Picture picture = new Picture();
                    picture.Name = "New York";
                    picture.UserOwner = "Kiwis";
                    pictureList.add(picture);
                }

                listener.onSuccess(pictureList);
            }
        }, 1000);
    }

}
