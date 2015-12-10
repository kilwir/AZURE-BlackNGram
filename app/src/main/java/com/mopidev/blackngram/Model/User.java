package com.mopidev.blackngram.Model;

import com.microsoft.azure.storage.table.TableEntity;
import com.microsoft.azure.storage.table.TableServiceEntity;

import java.util.UUID;

/**
 * Bad Boys Team
 * Created by remyjallan on 26/11/2015.
 */
public class User extends TableServiceEntity{

    String Username;
    String Password;

    public User(){
        this.partitionKey = Constante.PartitionKey;
        this.rowKey = UUID.randomUUID().toString();
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

}
