package com.android.brewnotes.recommendation

import com.android.brewnotes.service.RecommendationManager
import com.android.brewnotes.servicelayer.Recommendation
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jacobduron on 10/24/16.
 */
class RecommendationSummaryPresenter @Inject constructor(val recManager : RecommendationManager) {

    var summaryView : RecommenationSummaryView? = null

    fun attach(view : RecommenationSummaryView){
        summaryView = view
    }

    fun detach(){
        summaryView = null
    }


    fun loadRecs(id : String){
        recManager.loadRecommendations(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    {summaryView?.loadRecs(it)},
                    {},
                    {}
            )
    }


    public interface RecommenationSummaryView{
        fun loadRecs(list : List<Recommendation>?)
    }
}