package com.android.brewnotes.framework;

import android.app.Application;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import com.android.brewnotes.Injector;
import com.android.brewnotes.Modules;
import com.android.brewnotes.service.UserManager;

import java.io.FileReader;

import javax.inject.Inject;


/**
 * Created by jacobduron on 9/5/16.
 */
public class BrewApplication extends Application {

    private static BrewApplication instance;

    @Override public void onCreate() {
        super.onCreate();
        instance = this;

        Injector.init(Modules.list());

    }

    public BrewApplication getApplicationContext(){
        return instance;
    }

    public static BrewApplication getInstance(){
        return instance;
    }

}
