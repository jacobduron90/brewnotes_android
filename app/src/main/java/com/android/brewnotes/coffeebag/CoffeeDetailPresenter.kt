package com.android.brewnotes.coffeebag

import com.android.brewnotes.service.CoffeeBagManager
import com.android.brewnotes.service.CheckInManager
import com.android.brewnotes.servicelayer.CheckIn
import com.android.brewnotes.servicelayer.CoffeeBag
import com.android.brewnotes.servicelayer.Recommendation
import javax.inject.Inject

/**
 * Created by jacobduron on 10/17/16.
 */
class CoffeeDetailPresenter @Inject constructor(val cm : CoffeeBagManager, val rm : CheckInManager){

    var detailView : CoffeeDetailView? = null;
    var bag : CoffeeBag? = null;

    fun attach(detailView : CoffeeDetailView){
        this.detailView = detailView;
    }

    fun detach() {
        this.detailView = null;
    }

    fun getBagById(id : String){
        bag = cm.getBagById(id);

        if(bag == null){
            detailView?.errorLoadingBag()
        }else{

            detailView?.setBagDetails(bag)
        }
    }

    fun loadRecommendations() {
        var id = bag?._id;
        if(id != null){
            rm.loadCheckIns(id)
                    .subscribe(
                            {detailView?.setCheckIns(it)},
                            {detailView?.setCheckIns(null)},
                            {}
                    )
        }else{
            detailView?.setCheckIns(null);
        }

    }

   interface CoffeeDetailView {
        fun setBagDetails(bag : CoffeeBag?)
        fun errorLoadingBag()
        fun setCheckIns(recs : List<CheckIn>?)
    }
}