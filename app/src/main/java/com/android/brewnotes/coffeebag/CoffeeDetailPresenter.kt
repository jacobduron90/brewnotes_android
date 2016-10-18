package com.android.brewnotes.coffeebag

import com.android.brewnotes.service.CoffeeBagManager
import com.android.brewnotes.servicelayer.CoffeeBag
import javax.inject.Inject

/**
 * Created by jacobduron on 10/17/16.
 */
class CoffeeDetailPresenter @Inject constructor(val cm : CoffeeBagManager){

    var detailView : CoffeeDetailView? = null;

    fun attach(detailView : CoffeeDetailView){
        this.detailView = detailView;
    }

    fun detach() {
        this.detailView = null;
    }

    fun getBagById(id : String){
        var bag = cm.getBagById(id);
        if(bag == null){
            detailView?.errorLoadingBag()
        }else{
            detailView?.setBagDetails(bag)
        }
    }

   interface CoffeeDetailView {
        fun setBagDetails(bag : CoffeeBag?)
        fun errorLoadingBag()
    }
}