package com.mopidev.blackngram.Model;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

/**
 * Bad Boys Team
 * Created by remyjallan on 18/12/2015.
 */
public class DataHelper {

    public static CloudTable getCloudTable(String tableName,boolean createIfNotExist) throws URISyntaxException, InvalidKeyException, StorageException {

        CloudStorageAccount storageAccount =
                CloudStorageAccount.parse(Constante.getStorageConnectionString());

        // Create the table client.
        CloudTableClient tableClient = storageAccount.createCloudTableClient();

        // Create a cloud table object for the table.
        CloudTable cloudTable = tableClient.getTableReference(Constante.NameTablePicture);

        if(createIfNotExist){
            cloudTable.createIfNotExists();
        }

        return cloudTable;
    }
}
