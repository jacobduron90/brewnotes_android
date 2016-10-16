package com.android.brewnotes.coffeebag

import com.android.brewnotes.service.CoffeeBagManager
import com.android.brewnotes.service.UserManager
import com.android.brewnotes.servicelayer.CoffeeCompany
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by jacobduron on 10/15/16.
 */
class CoffeeCompanyListPresenter @Inject constructor(val coffeeManager : CoffeeBagManager){

    var bagView : CoffeeCompanyListView? = null;

    fun attach(bagView : CoffeeCompanyListView){
        this.bagView = bagView;
    }

    fun detach(){
        this.bagView = null;
    }

    fun loadBags(){
        coffeeManager.getCoffeeCompanies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    {bagView?.setListOfBags(it)},
                    {bagView?.showErrorLoadingList(it)},
                    {}
            )
    }


    interface CoffeeCompanyListView {
        fun setListOfBags(bagList : List<CoffeeCompany>?)
        fun showErrorLoadingList(t : Throwable?);
    }
}