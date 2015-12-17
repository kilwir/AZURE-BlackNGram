package com.mopidev.blackngram.Model;

/**
 * Bad Boys Team
 * Created by remyjallan on 03/12/2015.
 */
public class Constante {
    public static String AzureAccountName = "blackngram";
    public static String AzureAccountAccessKey = "yqki9JL7AzfpQQow6uInDN4pmpAxg/bVJR1/f0lYe4NFlrVGlRlUEKVb8pjrKNc6MWSuKJSWOJiIYR00nxWjSQ==";
    public static String ServiceBusEndPoint = "sb://blackngram.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAcc essKey=Rn5y2kB7ep3hTYSrpDlVP37kmzN8o7vA6JenV5d1mk0=";
    public static String ConnectionString = "UseDevelopmentStorage=true;";

    public static String PartitionKey = "INGESUP";

    /**
     * Table Name
     */
    public static String NameTableUser = "User";
    public static String NameTablePicture = "UserImage";
    public static String NameTableFavorite = "Favorite";

    public static String getStorageConnectionString() {
        return  "DefaultEndpointsProtocol=http;" +
                "AccountName=" + AzureAccountName + ";" +
                "AccountKey=" + AzureAccountAccessKey ;
    }
}
