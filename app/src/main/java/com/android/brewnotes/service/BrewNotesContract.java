package com.android.brewnotes.service;

import com.android.brewnotes.servicelayer.AuthenticateRequest;
import com.android.brewnotes.servicelayer.CoffeeBag;
import com.android.brewnotes.servicelayer.CoffeeCompany;
import com.android.brewnotes.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by jacobduron on 9/4/16.
 */
public interface BrewNotesContract {




    @GET("/api/companies/{id}/bags")
    public Observable<List<CoffeeBag>> getCoffeeBagsRx(@Header("authorization") String authToken, @Path("id") String id);

    @GET("/api/coffeebags")
    public Call<List<CoffeeBag>> getCoffeeBags(@Header("authorization") String authToken);


    @POST("/api/authenticate")
    public Observable<User> authenticateRx(@Body AuthenticateRequest auth);

    @POST("/api/authenticate")
    public Call<User> authenticate(@Body AuthenticateRequest auth);

    @GET("/api/companies")
    public Observable<List<CoffeeCompany>> getCoffeeCompaniesRx(@Header("authorization") String authtoken);

    @GET("/api/companies")
    public Call<List<CoffeeCompany>> getCoffeeCompanies(@Header("authorization") String authtoken);

}
