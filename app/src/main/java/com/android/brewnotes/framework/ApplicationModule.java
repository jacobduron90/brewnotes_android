package com.android.brewnotes.framework;

import com.android.brewnotes.coffeebag.CoffeeBagActivity;
import com.android.brewnotes.coffeebag.CoffeeBagDetailActivity;
import com.android.brewnotes.coffeebag.CompanyAdapter;
import com.android.brewnotes.coffeebag.CompanyActivity;
import com.android.brewnotes.dashboard.DashboardActivity;
import com.android.brewnotes.recommendation.AddRecommendationActivity;
import com.android.brewnotes.recommendation.RecommendationSummaryActivity;
import com.android.brewnotes.service.CoffeeBagManager;
import com.android.brewnotes.ErrorHandler;
import com.android.brewnotes.login.MainActivity;
import com.android.brewnotes.service.BrewNotesContract;
import com.android.brewnotes.service.UserManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
                ErrorHandler.class,
                CompanyActivity.class,
                RecommendationSummaryActivity.class,
                DashboardActivity.class,
                AddRecommendationActivity.class

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

    @Provides
    CompanyAdapter provideCompanyAdapter(){
        return new CompanyAdapter();
    }

    @Provides
    BrewNotesContract providesBrewContract(Retrofit restAdapter){
        return restAdapter.create(BrewNotesContract.class);
    }

    @Provides @Named("authToken") String providesAuthToken(UserManager um){
        return um.getAuthToken();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl("http://104.131.83.239/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }

}
