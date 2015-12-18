package com.mopidev.blackngram.Model;

import android.os.Parcelable;

import com.microsoft.azure.storage.table.TableServiceEntity;

import java.util.UUID;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */

public class UserImage extends TableServiceEntity{

    private String Name;
    private String UserRowKey;
    private String ImageURL;
    private String ThumbnailURL;
    private String BlackImageURL;
    private String BlackThumbnailURL;

    public UserImage() {
        this.partitionKey = Constante.PartitionKey;
        this.rowKey = UUID.randomUUID().toString();
    }

    public String getBlackThumbnailURL() {
        return BlackThumbnailURL;
    }

    public void setBlackThumbnailURL(String blackThumbnailURL) {
        BlackThumbnailURL = blackThumbnailURL;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserRowKey() {
        return UserRowKey;
    }

    public void setUserRowKey(String userRowKey) {
        UserRowKey = userRowKey;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getThumbnailURL() {
        return ThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        ThumbnailURL = thumbnailURL;
    }

    public String getBlackImageURL() {
        return BlackImageURL;
    }

    public void setBlackImageURL(String blackImageURL) {
        BlackImageURL = blackImageURL;
    }

}
