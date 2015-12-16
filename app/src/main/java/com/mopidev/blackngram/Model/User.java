package com.mopidev.blackngram.Model;

import com.microsoft.azure.storage.table.TableEntity;
import com.microsoft.azure.storage.table.TableServiceEntity;

import java.util.UUID;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public class User extends TableServiceEntity{

    String mUsername;
    String mPassword;

    public User(){
        this.partitionKey = Constante.PartitionKey;
        this.rowKey = UUID.randomUUID().toString();
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
