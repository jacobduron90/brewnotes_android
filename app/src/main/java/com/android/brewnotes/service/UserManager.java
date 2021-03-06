package com.android.brewnotes.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.brewnotes.servicelayer.AuthenticateRequest;

import com.android.brewnotes.framework.BrewApplication;
import com.android.brewnotes.servicelayer.User;
import com.google.gson.Gson;

import rx.Observable;
import rx.Subscriber;
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
    private User user;
    private Gson gson;


    public UserManager(BrewNotesContract contract, Gson gson){
        this.contract = contract;
        this.gson = gson;
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
                        saveUser(user);
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

    private void saveUser(User user){
        this.user = user;

        getUserSharedPrefs().edit().putString("user", gson.toJson(user)).apply();
    }

    public User getUser() {
        if(user == null){
            user = gson.fromJson(getPrefsString("user"), User.class);
        }


        if(user != null){
            user = new User();
            user.photo = new User.Photo();
            user.photo.profilePhoto = "http://johnsonlaird.com/assets/images/1456/1456_profile1_sm.jpg";
            user.firstName = "John";
            user.lastName = "Jupiter";
            user.followingCount = 32;
            user.checkInCount = 10;

        }

        return user;

    }

    private void saveAuthToken(String token){
        savePrefsString(PREFS_AUTH_TOKEN_KEY, token);
        authToken = token;
    }

    public void logout(){
        getUserSharedPrefs().edit().remove(PREFS_AUTH_TOKEN_KEY).apply();
        getUserSharedPrefs().edit().remove("user").apply();
        authToken = null;
    }


}
