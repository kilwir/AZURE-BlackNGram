package com.mopidev.blackngram.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.microsoft.azure.storage.table.TableServiceEntity;

import java.util.UUID;

/**
 * Bad Boys Team
 * Created by remyjallan on 27/11/2015.
 */

public class UserImage extends TableServiceEntity implements Parcelable{

    private String mUserRowKey;
    private String mImageURL;
    private String mThumbnailURL;
    private String mBlackImageURL;
    private String mBlackThumbnailURL;

    public Boolean IsFavorite;

    public UserImage() {
        this.partitionKey = Constante.PartitionKey;
        this.rowKey = UUID.randomUUID().toString();
        this.IsFavorite = false;
    }

    //For Parcelable
    private UserImage(Parcel in) {
        this.rowKey = in.readString();
        this.partitionKey = in.readString();
        this.etag = in.readString();
        mUserRowKey = in.readString();
        mImageURL = in.readString();
        mThumbnailURL = in.readString();
        mBlackImageURL = in.readString();
        mBlackThumbnailURL = in.readString();
        IsFavorite = Boolean.parseBoolean(in.readString());
    }

    public String getBlackThumbnailURL() {
        return mBlackThumbnailURL;
    }

    public void setBlackThumbnailURL(String blackThumbnailURL) {
        mBlackThumbnailURL = blackThumbnailURL;
    }

    public String getUserRowKey() {
        return mUserRowKey;
    }

    public void setUserRowKey(String userRowKey) {
        mUserRowKey = userRowKey;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        mThumbnailURL = thumbnailURL;
    }

    public String getBlackImageURL() {
        return mBlackImageURL;
    }

    public void setBlackImageURL(String blackImageURL) {
        mBlackImageURL = blackImageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.rowKey);
        parcel.writeString(this.partitionKey);
        parcel.writeString(this.etag);
        parcel.writeString(mUserRowKey);
        parcel.writeString(mImageURL);
        parcel.writeString(mThumbnailURL);
        parcel.writeString(mBlackImageURL);
        parcel.writeString(mBlackThumbnailURL);
        parcel.writeString(Boolean.toString(IsFavorite));
    }

    public static final Parcelable.Creator<UserImage> CREATOR
            = new Parcelable.Creator<UserImage>() {
        public UserImage createFromParcel(Parcel in) {
            return new UserImage(in);
        }

        public UserImage[] newArray(int size) {
            return new UserImage[size];
        }
    };
}
