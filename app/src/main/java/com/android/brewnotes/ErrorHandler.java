package com.android.brewnotes;

import android.app.Activity;
import android.content.Intent;

import com.android.brewnotes.login.MainActivity;
import com.android.brewnotes.service.UserManager;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by jacobduron on 9/5/16.
 */
public class ErrorHandler {

    @Inject static UserManager userManager;

    public static void handleError(Throwable error, Activity activity){
        if(error instanceof HttpException){
            HttpException httpException = (HttpException)error;
            if(httpException.code() == 403){
                bounceToLogin(activity);
            }
        }
    }

    public static void bounceToLogin(Activity activity){
        userManager.logout();
        Intent loginIntent = new Intent(activity, MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(loginIntent);
        activity.finish();
    }
}
