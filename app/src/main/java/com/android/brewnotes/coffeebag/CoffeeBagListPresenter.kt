package com.android.brewnotes.coffeebag

import android.util.Log
import com.android.brewnotes.ErrorHandler
import com.android.brewnotes.service.CoffeeBagManager
import com.android.brewnotes.servicelayer.CoffeeBag
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jacobduron on 10/16/16.
 */
class CoffeeBagListPresenter @Inject constructor(val coffeeManager : CoffeeBagManager){

    var bagView : CoffeeBagListView? = null;

    fun attach(bagView: CoffeeBagListView){
        this.bagView = bagView;
    }

    fun detach(){
        this.bagView = null;
    }

    fun getCoffeeBags(id : String) {
        coffeeManager.getCoffeeBagListNetwork(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { bagView?.displayBagList(it)},
                    { bagView?.displayErrorMessage(it)},
                    {}
                );

    }


    public interface CoffeeBagListView{
        fun displayBagList(bags : List<CoffeeBag>?);
        fun displayErrorMessage(t: Throwable);
    }
}