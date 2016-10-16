package com.android.brewnotes;

/**
 * Created by jacobduron on 10/12/16.
 */

import com.android.brewnotes.framework.ApplicationModule;


public class Modules {

    public static Object[] list(){
        Object[] moduleList =
                new Object[]{
                        new ApplicationModule()
                };
        return moduleList;

    }
}
