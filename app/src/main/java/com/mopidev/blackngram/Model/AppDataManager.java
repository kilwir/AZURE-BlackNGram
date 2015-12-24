package com.mopidev.blackngram.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.arasthel.asyncjob.AsyncJob;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.mopidev.blackngram.Listener.OnCheckUserExistListener;
import com.mopidev.blackngram.Listener.OnLoadPicturesFinishedListener;
import com.mopidev.blackngram.Listener.OnLoadUserListener;
import com.mopidev.blackngram.Listener.OnLoginFinishedListener;
import com.mopidev.blackngram.Listener.OnSigninFinishedListener;

import java.util.ArrayList;
import java.util.Iterator;
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
    private static final String PREFERENCE_NAME_ROWKEY = PREFERENCE_NAME + "_ROWKEY";

    private static AppDataManager ourInstance = new AppDataManager();

    public static AppDataManager getInstance() {
        return ourInstance;
    }

    private AppDataManager() {}

    /**
     * Fonction Utilisateur
     */

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
                            CloudTable cloudTable = DataHelper.getCloudTable(Constante.NameTableUser, true);

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
                    CloudTable cloudTable = DataHelper.getCloudTable(Constante.NameTableUser, false);

                    String UsernameFilter = TableQuery.generateFilterCondition("Username", TableQuery.QueryComparisons.EQUAL, user.getUsername());
                    String PartitionKeyFilter = TableQuery.generateFilterCondition("PartitionKey", TableQuery.QueryComparisons.EQUAL, Constante.PartitionKey);

                    String combinedFilter = TableQuery.combineFilters(
                            UsernameFilter, TableQuery.Operators.AND, PartitionKeyFilter);

                    TableQuery<User> userQuery = TableQuery.from(User.class).where(combinedFilter);

                    Iterator<User> userIterator = cloudTable.execute(userQuery).iterator();

                    if (!userIterator.hasNext()) {
                        listener.UserDoesNotExist();
                    } else {
                        listener.UserAlreadyExist();
                    }
                } catch (Exception e) {
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
                    CloudTable cloudTable = DataHelper.getCloudTable(Constante.NameTableUser,false);

                    String UsernameFilter = TableQuery.generateFilterCondition("Username", TableQuery.QueryComparisons.EQUAL, user.getUsername());
                    String PasswordFilter = TableQuery.generateFilterCondition("Password", TableQuery.QueryComparisons.EQUAL, user.getPassword());
                    String PartitionKeyFilter = TableQuery.generateFilterCondition("PartitionKey", TableQuery.QueryComparisons.EQUAL, Constante.PartitionKey);

                    String combinedFilterCredential = TableQuery.combineFilters(
                            UsernameFilter, TableQuery.Operators.AND, PasswordFilter);

                    String combinedFilter = TableQuery.combineFilters(combinedFilterCredential,TableQuery.Operators.AND,PartitionKeyFilter);

                    TableQuery<User> userTableQuery = TableQuery.from(User.class).where(combinedFilter);

                    Iterator<User> userIterator = cloudTable.execute(userTableQuery).iterator();

                    if(userIterator.hasNext()) {
                        final User currentUser = userIterator.next();
                        saveCurrentUser(currentUser,context);
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

    public void getUserById(final String userId,final OnLoadUserListener listener) {
        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                try {
                    CloudTable cloudTable = DataHelper.getCloudTable(Constante.NameTableUser, false);

                    String RowKeyFilter = TableQuery.generateFilterCondition("RowKey", TableQuery.QueryComparisons.EQUAL, userId);
                    String PartitionKeyFilter = TableQuery.generateFilterCondition("PartitionKey", TableQuery.QueryComparisons.EQUAL, Constante.PartitionKey);

                    String combinedFilter = TableQuery.combineFilters(
                            RowKeyFilter, TableQuery.Operators.AND, PartitionKeyFilter);

                    TableQuery<User> userQuery = TableQuery.from(User.class).where(combinedFilter);

                    final Iterator<User> userIterator = cloudTable.execute(userQuery).iterator();

                    if (userIterator.hasNext()) {
                        AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                            @Override
                            public void doInUIThread() {
                                listener.OnSuccess(userIterator.next());
                            }
                        });
                    } else {
                        AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                            @Override
                            public void doInUIThread() {
                                listener.OnError();
                            }
                        });
                    }
                } catch (Exception e) {
                    AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                        @Override
                        public void doInUIThread() {
                            listener.OnError();
                        }
                    });
                }
            }
        });
    }

    private void saveCurrentUser(User user,Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(PREFERENCE_NAME_USERNAME,user.getUsername());
        editor.putString(PREFERENCE_NAME_PASSWORD, user.getPassword());
        editor.putString(PREFERENCE_NAME_ROWKEY, user.getRowKey());

        editor.apply();
    }

    public void getCurrentUser(Context context,OnLoginFinishedListener listener){
        User currentUser = new User();
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);

        currentUser.setUsername(settings.getString(PREFERENCE_NAME_USERNAME, null));
        currentUser.setPassword(settings.getString(PREFERENCE_NAME_PASSWORD, ""));
        currentUser.setRowKey(settings.getString(PREFERENCE_NAME_ROWKEY, null));

        if( currentUser.getUsername() != null )
            listener.onLoginSuccess(currentUser);

    }

    public User getCurrentUser(Context context) {
        User currentUser = new User();
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);

        currentUser.setUsername(settings.getString(PREFERENCE_NAME_USERNAME, null));
        currentUser.setPassword(settings.getString(PREFERENCE_NAME_PASSWORD, ""));
        currentUser.setRowKey(settings.getString(PREFERENCE_NAME_ROWKEY, null));

        if( currentUser.getUsername() != null )
            return currentUser;
        else
            return null;
    }

    public void logout(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.remove(PREFERENCE_NAME_USERNAME);
        editor.remove(PREFERENCE_NAME_PASSWORD);
        editor.remove(PREFERENCE_NAME_ROWKEY);

        editor.apply();
    }

    /**
     * Fin Fonction Utilisateur
     */


    public void loadAllPictures(Context context,final OnLoadPicturesFinishedListener listener){
        final User currentUser = AppDataManager.getInstance().getCurrentUser(context);

        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                try {

                    CloudTable cloudTable = DataHelper.getCloudTable(Constante.NameTablePicture, false);

                    String RowKeyFilter = TableQuery.generateFilterCondition("UserRowKey", TableQuery.QueryComparisons.NOT_EQUAL, currentUser.getRowKey());
                    String BlackUrlFilter = TableQuery.generateFilterCondition("BlackImageURL", TableQuery.QueryComparisons.NOT_EQUAL, "");

                    String combinedFilter = TableQuery.combineFilters(
                            RowKeyFilter, TableQuery.Operators.AND, BlackUrlFilter);

                    TableQuery<UserImage> pictureTableQuery = TableQuery.from(UserImage.class).where(combinedFilter);

                    Iterator<UserImage> pictureIterator = cloudTable.execute(pictureTableQuery).iterator();

                    final List<UserImage> userImageList = new ArrayList<>();

                    CloudTable cloudTableFavorite = DataHelper.getCloudTable(Constante.NameTableFavorite, false);

                    while (pictureIterator.hasNext()) {

                        UserImage currentImage = pictureIterator.next();

                        RowKeyFilter = TableQuery.generateFilterCondition("UserRowKey", TableQuery.QueryComparisons.EQUAL, currentUser.getRowKey());
                        String UserImageFilter = TableQuery.generateFilterCondition("UserImageRowKey", TableQuery.QueryComparisons.EQUAL, currentImage.getRowKey());

                        combinedFilter = TableQuery.combineFilters(RowKeyFilter, TableQuery.Operators.AND, UserImageFilter);

                        TableQuery<UserFavorite> favoritetableQuery = TableQuery.from(UserFavorite.class).where(combinedFilter);

                        Iterator<UserFavorite> favoriteIterator = cloudTableFavorite.execute(favoritetableQuery).iterator();

                        if (favoriteIterator.hasNext()) {
                            currentImage.IsFavorite = true;
                        }

                        userImageList.add(currentImage);
                    }


                    AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                        @Override
                        public void doInUIThread() {
                            listener.onSuccess(userImageList);
                        }
                    });

                } catch (Exception e) {
                    AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                        @Override
                        public void doInUIThread() {
                            listener.onError();
                        }
                    });
                }
            }
        });
    }

    public void addFavorite(UserImage userImage,Context context){

        User currentUser =  AppDataManager.getInstance().getCurrentUser(context);

        final UserFavorite newFavorite = new UserFavorite();
        newFavorite.setUserImageRowKey(userImage);
        newFavorite.setUserRowKey(currentUser);

        if(!userImage.IsFavorite) {
            AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
                @Override
                public void doOnBackground() {
                    try {

                        CloudTable cloudTable = DataHelper.getCloudTable(Constante.NameTableFavorite, true);

                        TableOperation insertNewFavorite = TableOperation.insert(newFavorite);

                        cloudTable.execute(insertNewFavorite);
                        Log.d(TAG, "Favorite Added");
                    } catch( Exception e) {
                        Log.d(TAG, "Exception addFavorite");
                    }
                }
            });
        }
    }

    public void deleteFavorite(final UserImage userImage,Context context){

        final User currentUser =  AppDataManager.getInstance().getCurrentUser(context);

        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                try {
                    CloudTable cloudTable = DataHelper.getCloudTable(Constante.NameTableFavorite, false);
                    //TableOperation deleteFavorite = TableOperation.delete(userImage.Favorite);

                    String UserFilter = TableQuery.generateFilterCondition("UserRowKey", TableQuery.QueryComparisons.EQUAL, currentUser.getRowKey());
                    String UserImageFilter = TableQuery.generateFilterCondition("UserImageRowKey", TableQuery.QueryComparisons.EQUAL, userImage.getRowKey());

                    String combinedFilter = TableQuery.combineFilters(UserFilter, TableQuery.Operators.AND, UserImageFilter);

                    TableQuery<UserFavorite> favoriteTableQuery = TableQuery.from(UserFavorite.class).where(combinedFilter);

                    Iterator<UserFavorite> favoriteIterator = cloudTable.execute(favoriteTableQuery).iterator();

                    //While pour supprimer d'eventuel doublons
                    while (favoriteIterator.hasNext()) {
                        TableOperation delete = TableOperation.delete(favoriteIterator.next());
                        cloudTable.execute(delete);
                    }

                    Log.d(TAG,"Favorite delete");
                } catch (Exception e) {
                    Log.d(TAG,e.getMessage());
                }
            }
        });
    }


}
