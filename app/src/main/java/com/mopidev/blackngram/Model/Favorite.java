package com.mopidev.blackngram.Model;

import com.microsoft.azure.storage.table.TableServiceEntity;

import java.util.UUID;

/**
 * Bad Boys Team
 * Created by remyjallan on 17/12/2015.
 */
public class Favorite extends TableServiceEntity {
    private String mUserId;
    private String mPictureId;

    public Favorite(){
        this.partitionKey = Constante.PartitionKey;
        this.rowKey = UUID.randomUUID().toString();
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getPictureId() {
        return mPictureId;
    }

    public void setPictureId(String pictureId) {
        mPictureId = pictureId;
    }

    public void setPictureId(Picture picture) {
        this.mPictureId = picture.getRowKey();
    }

    public void setUserId(User user) {
        this.mUserId = user.getRowKey();
    }
}
