package com.mopidev.blackngram.Model;

/**
 * Bad Boys Team
 * Created by remyjallan on 12/12/2015.
 */
public class ErrorCode {
    public static final int USER_ALREADY_EXIST = 0;

    public static final int WRONG_CREDENTIAL = 1;

    public static String getMessage(int code){

        String message  = null;

        switch (code) {
            case USER_ALREADY_EXIST :
                message = "User already exist";
                break;
            case WRONG_CREDENTIAL :
                message = "Wrong username/password";
                break;
        }

        return message;
    }
}
