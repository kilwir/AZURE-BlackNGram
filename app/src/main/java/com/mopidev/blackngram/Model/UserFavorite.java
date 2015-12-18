package com.mopidev.blackngram.Model;

import com.microsoft.azure.storage.table.TableServiceEntity;

import java.util.UUID;

/**
 * Bad Boys Team
 * Created by remyjallan on 17/12/2015.
 */
public class UserFavorite extends TableServiceEntity {
    private String mUserRowKey;
    private String mUserImageRowKey;

    public UserFavorite(){
        this.partitionKey = Constante.PartitionKey;
        this.rowKey = UUID.randomUUID().toString();
    }

    public String getUserRowKey() {
        return mUserRowKey;
    }

    public void setUserRowKey(String userId) {
        mUserRowKey = userId;
    }

    public String getUserImageRowKey() {
        return mUserImageRowKey;
    }

    public void setUserImageRowKey(String pictureId) {
        mUserImageRowKey = pictureId;
    }

    public void setUserImageRowKey(UserImage userImage) {
        this.mUserImageRowKey = userImage.getRowKey();
    }

    public void setUserRowKey(User user) {
        this.mUserRowKey = user.getRowKey();
    }
}
