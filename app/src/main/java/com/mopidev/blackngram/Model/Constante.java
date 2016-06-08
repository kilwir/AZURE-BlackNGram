package com.mopidev.blackngram.Model;

/**
 * Bad Boys Team
 * Created by remyjallan on 03/12/2015.
 */
public class Constante {
    public static String AzureAccountName = "YOUR_ACCOUNT_NAME";
    public static String AzureAccountAccessKey = "YOUR_ACCOUNT_ACCESS_KEY";
    public static String ServiceBusEndPoint = "sb://blackngram.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAcc essKey=Rn5y2kB7ep3hTYSrpDlVP37kmzN8o7vA6JenV5d1mk0=";
    public static String ConnectionString = "UseDevelopmentStorage=true;";

    public static String PartitionKey = "YOUR_PARTITION_KEY";

    /**
     * Table Name
     */
    public static String NameTableUser = "User";
    public static String NameTablePicture = "UserImage";
    public static String NameTableFavorite = "UserFavorite";

    /**
     * Blob Name
     */
    public static String NameBlobImage = "images";


    /**
     * Queue Name
     */
    public static String QueueBlobName = "thumbnailrequest";

    /**
     *  Application String
     */

    public static String NameExtraFullScreenImage = "pictureUrl";
    public static String NameExtraFavoriteImages = "favoritesImage";
    public static String NameExtraLoginDisconnect = "isDisconnect";

    public static String getStorageConnectionString() {
        return  "DefaultEndpointsProtocol=http;" +
                "AccountName=" + AzureAccountName + ";" +
                "AccountKey=" + AzureAccountAccessKey ;
    }
}
