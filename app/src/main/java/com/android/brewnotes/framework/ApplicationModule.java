package com.android.brewnotes.framework;

import com.android.brewnotes.coffeebag.CoffeeBagActivity;
import com.android.brewnotes.coffeebag.CoffeeBagDetailActivity;
import com.android.brewnotes.login.LoginPresenter;
import com.android.brewnotes.service.CoffeeBagManager;
import com.android.brewnotes.ErrorHandler;
import com.android.brewnotes.login.MainActivity;
import com.android.brewnotes.service.BrewNotesContract;
import com.android.brewnotes.service.UserManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jacobduron on 10/12/16.
 */
@Module(
        library = true,
        complete = false,
        injects = {
                MainActivity.class,
                CoffeeBagActivity.class,
                CoffeeBagDetailActivity.class,
                ErrorHandler.class

        },
        staticInjections = {
                ErrorHandler.class
        }
)
public class ApplicationModule {


    @Provides
    @Singleton
    UserManager provideUserManager(BrewNotesContract contract){
        return new UserManager(contract);
    }

    @Provides
    @Singleton
    CoffeeBagManager providesCoffeeBagManager(UserManager userManager, BrewNotesContract contract){
        return new CoffeeBagManager(userManager, contract);
    }

}
