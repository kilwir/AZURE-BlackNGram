package com.mopidev.blackngram.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.arasthel.asyncjob.AsyncJob;
import com.google.common.collect.Iterables;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageUri;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.mopidev.blackngram.Listener.OnCheckUserExistListener;
import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Listener.OnLoginFinishedListener;
import com.mopidev.blackngram.Listener.OnSigninFinishedListener;
import com.mopidev.blackngram.Presenter.LoginPresenter;

import java.net.ContentHandler;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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

    public void signIn(final User user,final Context context,final OnSigninFinishedListener listener){

        this.checkUserExist(user, new OnCheckUserExistListener() {
            @Override
            public void UserAlreadyExist() {
                AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                    @Override
                    public void doInUIThread() {
                        listener.onSigninError(ErrorCode.USER_ALREADY_EXIST);
                    }
                });
            }

            @Override
            public void UserDoesNotExist() {
                AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
                    @Override
                    public void doOnBackground() {
                        try {
                            CloudStorageAccount storageAccount =
                                    CloudStorageAccount.parse(Constante.getStorageConnectionString());
                            CloudTableClient tableClient = storageAccount.createCloudTableClient();
                            CloudTable cloudTable = tableClient.getTableReference(Constante.NameTableUser);

                            cloudTable.createIfNotExists();

                            TableOperation insertNewUser = TableOperation.insert(user);

                            cloudTable.execute(insertNewUser);

                            saveCurrentUser(user, context);

                            AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                                @Override
                                public void doInUIThread() {
                                    listener.onSigninSuccess(user);
                                }
                            });
                        } catch (Exception e) {
                            Log.d(TAG, "Exception");
                            AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                                @Override
                                public void doInUIThread() {
                                    listener.onSigninError(ErrorCode.ERROR_CONNECTION);
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void CheckUserExistError(final int errorCode) {
                AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                    @Override
                    public void doInUIThread() {
                        listener.onSigninError(errorCode);
                    }
                });
            }
        });
    }

    private void checkUserExist(final User user,final OnCheckUserExistListener listener){
        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                try {
                    CloudStorageAccount storageAccount =
                            CloudStorageAccount.parse(Constante.getStorageConnectionString());

                    // Create the table client.
                    CloudTableClient tableClient = storageAccount.createCloudTableClient();

                    // Create a cloud table object for the table.
                    CloudTable cloudTable = tableClient.getTableReference(Constante.NameTableUser);

                    String UsernameFilter = TableQuery.generateFilterCondition("Username", TableQuery.QueryComparisons.EQUAL, user.getUsername());
                    String PartitionKeyFilter = TableQuery.generateFilterCondition("PartitionKey",TableQuery.QueryComparisons.EQUAL,Constante.PartitionKey);

                    String combinedFilter = TableQuery.combineFilters(
                            UsernameFilter, TableQuery.Operators.AND, PartitionKeyFilter);

                    TableQuery<User> userQuery = TableQuery.from(User.class).where(combinedFilter);

                    Iterator<User> userIterator = cloudTable.execute(userQuery).iterator();

                    if(!userIterator.hasNext()) {
                        listener.UserDoesNotExist();
                    } else {
                        listener.UserAlreadyExist();
                    }
                } catch(Exception e) {
                    listener.CheckUserExistError(ErrorCode.ERROR_CONNECTION);
                }
            }
        });
    }

    public void login(final User user, final Context context,final OnLoginFinishedListener listener) {

        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                try {
                    CloudStorageAccount storageAccount =
                            CloudStorageAccount.parse(Constante.getStorageConnectionString());

                    // Create the table client.
                    CloudTableClient tableClient = storageAccount.createCloudTableClient();

                    // Create a cloud table object for the table.
                    CloudTable cloudTable = tableClient.getTableReference(Constante.NameTableUser);

                    String UsernameFilter = TableQuery.generateFilterCondition("Username", TableQuery.QueryComparisons.EQUAL, user.getUsername());
                    String PasswordFilter = TableQuery.generateFilterCondition("Password", TableQuery.QueryComparisons.EQUAL, user.getPassword());
                    String PartitionKeyFilter = TableQuery.generateFilterCondition("PartitionKey", TableQuery.QueryComparisons.EQUAL, Constante.PartitionKey);

                    String combinedFilterCredential = TableQuery.combineFilters(
                            UsernameFilter, TableQuery.Operators.AND, PasswordFilter);

                    String combinedFilter = TableQuery.combineFilters(combinedFilterCredential,TableQuery.Operators.AND,PartitionKeyFilter);

                    Log.d(TAG,combinedFilter);

                    TableQuery<User> userTableQuery = TableQuery.from(User.class).where(combinedFilter);

                    Iterator<User> userIterator = cloudTable.execute(userTableQuery).iterator();

                    if(userIterator.hasNext()) {
                        final User currentUser = userIterator.next();
                        saveCurrentUser(user,context);
                        AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                            @Override
                            public void doInUIThread() {
                                listener.onLoginSuccess(currentUser);
                            }
                        });
                    } else {
                        AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                            @Override
                            public void doInUIThread() {
                                listener.onLoginError(ErrorCode.WRONG_CREDENTIAL);
                            }
                        });
                    }
                } catch(Exception e) {
                    AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                        @Override
                        public void doInUIThread() {
                            listener.onLoginError(ErrorCode.ERROR_CONNECTION);
                        }
                    });
                }
            }
        });
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
            listener.onLoginSuccess(currentUser);

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

                    picture.Like = i % 2 == 0;

                    pictureList.add(picture);
                }

                listener.onSuccess(pictureList);
            }
        }, 1000);
    }

}
