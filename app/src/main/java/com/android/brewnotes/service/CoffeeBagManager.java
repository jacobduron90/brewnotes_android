package com.android.brewnotes.service;

import android.util.Log;

import com.android.brewnotes.service.BrewNotesApi;
import com.android.brewnotes.service.BrewNotesContract;
import com.android.brewnotes.service.UserManager;
import com.android.brewnotes.servicelayer.CoffeeBag;
import com.android.brewnotes.servicelayer.CoffeeCompany;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by jacobduron on 9/5/16.
 */
public class CoffeeBagManager{

    UserManager userManager;
    BrewNotesContract contract;

    @Inject
    public CoffeeBagManager(UserManager userManager, BrewNotesContract brewNotesContract){
        this.userManager = userManager;
        this.contract = brewNotesContract;
    }

    public Observable<List<CoffeeBag>> getCoffeeBagListNetwork(String companyId){
        Log.d("COffeeBagmanager", "authtoken: " + userManager.getAuthToken());
        return BrewNotesApi.getInstance().getApi().getCoffeeBagsRx(userManager.getAuthToken(), companyId);
    }

    public Observable<List<CoffeeBag>> getCoffeeBagListCache(){
        return null;
    }

    public Observable<List<CoffeeBag>> getCoffeeBagListMem(){
        return null;
    }

    public Observable<List<CoffeeCompany>> getCoffeeCompanies(){
        return BrewNotesApi.getInstance().getApi().getCoffeeCompaniesRx(userManager.getAuthToken());
    }
 }
