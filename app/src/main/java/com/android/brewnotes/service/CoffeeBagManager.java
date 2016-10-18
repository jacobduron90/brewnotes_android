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
import rx.functions.Action1;

/**
 * Created by jacobduron on 9/5/16.
 */
public class CoffeeBagManager{

    UserManager userManager;
    BrewNotesContract contract;
    List<CoffeeBag> bags = null;


    @Inject
    public CoffeeBagManager(UserManager userManager, BrewNotesContract brewNotesContract){
        this.userManager = userManager;
        this.contract = brewNotesContract;
    }

    public Observable<List<CoffeeBag>> getCoffeeBagListNetwork(String companyId){
        Log.d("COffeeBagmanager", "authtoken: " + userManager.getAuthToken());
        return BrewNotesApi
                .getInstance()
                .getApi()
                .getCoffeeBagsRx(userManager.getAuthToken(), companyId)
                .doOnNext(new Action1<List<CoffeeBag>>() {
                    @Override
                    public void call(List<CoffeeBag> coffeeBags) {
                        CoffeeBagManager.this.bags = coffeeBags;
                    }
                });
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

    public CoffeeBag getBagById (String id){
        if(bags == null || bags.isEmpty() || id == null){
            return null;
        }
        for(CoffeeBag bag : bags){
            if(bag._id.equals(id)){
                return bag;
            }
        }
        return null;
    }
 }
