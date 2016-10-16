package com.android.brewnotes;

/**
 * Created by jacobduron on 10/12/16.
 */

import com.android.brewnotes.dashboard.DashboardModule;
import com.android.brewnotes.framework.ApplicationModule;


public class Modules {

    public static Object[] list(){
        Object[] moduleList =
                new Object[]{
                        new DashboardModule(),
                        new ApplicationModule()
                };
        return moduleList;

    }
}
