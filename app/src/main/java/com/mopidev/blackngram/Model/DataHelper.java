package com.mopidev.blackngram.Model;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Date;
import java.util.UUID;

/**
 * Bad Boys Team
 * Created by remyjallan on 18/12/2015.
 */
public class DataHelper {

    public static CloudTable getCloudTable(String tableName) throws URISyntaxException, InvalidKeyException, StorageException {

        CloudStorageAccount storageAccount =
                CloudStorageAccount.parse(Constante.getStorageConnectionString());

        // Create the table client.
        CloudTableClient tableClient = storageAccount.createCloudTableClient();

        // Create a cloud table object for the table.
        CloudTable cloudTable = tableClient.getTableReference(tableName);

        cloudTable.createIfNotExists();

        return cloudTable;
    }

    public static CloudBlobContainer getCloudBlob(String blobName) throws URISyntaxException, InvalidKeyException, StorageException {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(Constante.getStorageConnectionString());

        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

        CloudBlobContainer container = blobClient.getContainerReference("images");

        container.createIfNotExists();

        return container;
    }

    public static String getPictureName(){
        return "Picture-" + UUID.randomUUID().toString();
    }
}
