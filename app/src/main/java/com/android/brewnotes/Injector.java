package com.android.brewnotes;

import dagger.Module;
import dagger.ObjectGraph;

/**
 * Created by jacobduron on 10/12/16.
 */

public class Injector {

    static ObjectGraph objectGraph = null;

    private static void create(Object... modules){
        objectGraph = ObjectGraph.create(modules);
        objectGraph.injectStatics();
    }

    public static void init(Object... modules){
        create(modules);
    }

    public static void inject(final Object object){
        objectGraph.inject(object);
    }
}
