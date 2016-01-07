package com.mopidev.blackngram.Model;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static CloudQueue getCloudQueue(String queueName) throws URISyntaxException, StorageException, InvalidKeyException {
        // Retrieve storage account from connection-string.
        CloudStorageAccount storageAccount =
                CloudStorageAccount.parse(Constante.getStorageConnectionString());

        // Create the queue client.
        CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

        // Retrieve a reference to a queue.
        CloudQueue queue = queueClient.getQueueReference(queueName);

        // Create the queue if it doesn't already exist.
        queue.createIfNotExists();

        return queue;
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

    public static String getJsonQueue(CloudBlockBlob blob,UserImage image) throws JSONException {
        JSONObject blobJson = new JSONObject();
        blobJson.put("UserImageRowKey", image.getRowKey());
        blobJson.put("BlobUri",blob.getUri().toString());

        return blobJson.toString();
    }
}
