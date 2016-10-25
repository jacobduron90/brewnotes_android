package com.android.brewnotes.service

import com.android.brewnotes.servicelayer.Recommendation
import rx.Observable
import java.util.Date
import javax.inject.Inject


/**
 * Created by jacobduron on 10/19/16.
 */
class RecommendationManager @Inject constructor(){

    var recommendations : MutableList<Recommendation>? = null;


    private fun hydrateRecommendations () {
        recommendations = mutableListOf();
        recommendations?.add(Recommendation("Great bag of coffee!", Date(), "john bob", "http://www.iconsfind.com/wp-content/uploads/2015/10/20151012_561baed03a54e.png"));
        recommendations?.add(Recommendation("I would keep the water temp below 200 for this one.", Date(), "ice queen", "https://s-media-cache-ak0.pinimg.com/564x/25/d6/78/25d678f123cb4d392f3e2d66f1d342cc.jpg"));
        recommendations?.add(Recommendation("Pretty goood", Date(), "purty", "https://s-media-cache-ak0.pinimg.com/564x/25/d6/78/25d678f123cb4d392f3e2d66f1d342cc.jpg"));
        recommendations?.add(Recommendation("I used this bag for ice coffee", Date(), "mice queen", "https://cdn3.iconfinder.com/data/icons/glypho-generic-icons/64/user-woman-512.png"));
        recommendations?.add(Recommendation("A little too bitter for my taste", Date(), "likes it sweet", "https://cdn4.iconfinder.com/data/icons/business-men-women-set-1/512/23-512.png"));

    }


    fun loadRecommendations(id : String) : Observable<List<Recommendation>> {

        var memory : Observable<List<Recommendation>> = loadRecommendationMemory()
        var disk : Observable<List<Recommendation>> = loadRecommendationsDisk()
        val full : Observable<List<Recommendation>> = Observable.concat(memory, disk).first { it != null }
        return full
    }


    private fun loadRecommendationMemory() : Observable<List<Recommendation>> {
        hydrateRecommendations()
        return Observable.just(recommendations)
    }

    private fun loadRecommendationsDisk() : Observable<List<Recommendation>>{
        return Observable.just(listOf(Recommendation("", Date(), "", "")))
    }

}