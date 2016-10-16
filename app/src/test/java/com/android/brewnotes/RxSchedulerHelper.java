package com.android.brewnotes;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by jacobduron on 10/16/16.
 */

public class RxSchedulerHelper{

    public static void setup(){
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    public static void teardown(){
        RxAndroidPlugins.getInstance().reset();
    }
}
