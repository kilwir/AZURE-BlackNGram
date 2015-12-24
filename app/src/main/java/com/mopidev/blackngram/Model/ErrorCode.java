package com.mopidev.blackngram.Model;

/**
 * Bad Boys Team
 * Created by remyjallan on 12/12/2015.
 */
public class ErrorCode {

    public static final int USER_ALREADY_EXIST  = 0;
    public static final int WRONG_CREDENTIAL    = 1;
    public static final int ERROR_CONNECTION    = 2;

    public static String getMessage(int code){

        String message  = "";

        switch (code) {
            case USER_ALREADY_EXIST :
                message = "Username already exist";
                break;
            case WRONG_CREDENTIAL :
                message = "Wrong username/password";
                break;
            case ERROR_CONNECTION :
                message = "Error connection";
                break;
        }

        return message;
    }
}
