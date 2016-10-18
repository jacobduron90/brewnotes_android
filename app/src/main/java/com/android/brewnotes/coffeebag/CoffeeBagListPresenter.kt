package com.android.brewnotes.coffeebag

import com.android.brewnotes.service.CoffeeBagManager
import com.android.brewnotes.servicelayer.CoffeeBag
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jacobduron on 10/16/16.
 */
class CoffeeBagListPresenter @Inject constructor(val coffeeManager : CoffeeBagManager){

    var bagView : CoffeeBagListView? = null;
    var subscription : Subscription? = null

    fun attach(bagView: CoffeeBagListView){
        this.bagView = bagView;
    }

    fun detach(){
        this.bagView = null;
        if(subscription != null && !subscription!!.isUnsubscribed){
            subscription?.unsubscribe()
        }
    }

    fun getCoffeeBags(id : String) {
        subscription = coffeeManager.getCoffeeBagListNetwork(id)
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