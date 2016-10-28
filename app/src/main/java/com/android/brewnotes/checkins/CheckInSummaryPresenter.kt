package com.android.brewnotes.checkins

import com.android.brewnotes.service.CheckInManager
import com.android.brewnotes.servicelayer.CheckIn
import com.android.brewnotes.servicelayer.Recommendation
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jacobduron on 10/24/16.
 */
class CheckInSummaryPresenter @Inject constructor(val recManager : CheckInManager) {

    var summaryView : RecommenationSummaryView? = null

    fun attach(view : RecommenationSummaryView){
        summaryView = view
    }

    fun detach(){
        summaryView = null
    }


    fun loadChecks(id : String){
        recManager.loadCheckIns(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    {summaryView?.setChecks(it)},
                    {},
                    {}
            )
    }


    public interface RecommenationSummaryView{
        fun setChecks(list : List<CheckIn>?)
    }
}