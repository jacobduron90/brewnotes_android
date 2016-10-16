package com.android.brewnotes.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.brewnotes.servicelayer.AuthenticateRequest;

import com.android.brewnotes.framework.BrewApplication;
import com.android.brewnotes.User;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by jacobduron on 9/5/16.
 */
public class UserManager{
    private static final String TAG = "USER_MANAGER";
    private static final String PREFS_KEY = "USER_SHARED_PREFS";
    private static final String PREFS_AUTH_TOKEN_KEY = "USER_SHARED_PREFS_AUTH_TOKEN_KEY";

    private String authToken;
    private BrewNotesContract contract;

    public UserManager(BrewNotesContract contract){
        this.contract = contract;
    }


    public Observable<User> login(String email, String password){
        AuthenticateRequest auth = new AuthenticateRequest();
        auth.email = email;
        auth.password = password;
        return contract.authenticateRx(auth)
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Log.d(TAG, "on Next called");
                        saveAuthToken(user.token);
                    }
                });
    }

    public String getAuthToken() {
        if(authToken == null){
            return getPrefsString(PREFS_AUTH_TOKEN_KEY);
        }
        return authToken;
    }

    private String getPrefsString(String KEY){
       return getUserSharedPrefs()
            .getString(KEY, null);

    }

    private void savePrefsString(String KEY, String value){
       getUserSharedPrefs()
                .edit().putString(KEY, value).apply();
    }

    private SharedPreferences getUserSharedPrefs(){
        return  BrewApplication.getInstance().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
    }

    private void saveAuthToken(String token){
        savePrefsString(PREFS_AUTH_TOKEN_KEY, token);
        authToken = token;
    }

    public void logout(){
        getUserSharedPrefs().edit().remove(PREFS_AUTH_TOKEN_KEY).apply();
        authToken = null;
    }
}
