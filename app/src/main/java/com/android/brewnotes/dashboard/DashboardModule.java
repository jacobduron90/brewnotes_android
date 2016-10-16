package com.android.brewnotes.dashboard;

import com.android.brewnotes.coffeebag.CoffeeBagActivity;
import com.android.brewnotes.servicelayer.CoffeeCompany;
import com.android.brewnotes.service.BrewNotesContract;
import com.android.brewnotes.service.UserManager;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by jacobduron on 10/12/16.
 */

@Module(
        library = true,
        complete = false,
        injects = {
                DashboardActivity.class,
                CoffeeBagActivity.class

        }
)
public class DashboardModule {

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
