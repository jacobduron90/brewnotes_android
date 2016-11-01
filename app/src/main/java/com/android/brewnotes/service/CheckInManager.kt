package com.android.brewnotes.service

import com.android.brewnotes.servicelayer.CheckIn
import com.android.brewnotes.servicelayer.Recommendation
import com.bumptech.glide.Glide
import rx.Observable
import java.util.Date


/**
 * Created by jacobduron on 10/19/16.
 */
class CheckInManager constructor(){

    var checkins : MutableList<CheckIn>? = null;



    private fun hydrateRecommendations () {
        checkins = mutableListOf()
        var recommendation1 = Recommendation("Great bag of coffee!", Date())
        recommendation1.overallScore = 8;
        var recommendation2 = Recommendation("I would keep the water temp below 200 for this one.", Date())
        recommendation2.overallScore = 4
        var recommendation3 = Recommendation("Too bitter", Date())
        recommendation3.overallScore = 7


        checkins?.add(CheckIn("1", "57db1c91483bec7b0a70e7bc", "cuvee", "/img/grizz.jpg", "john bob", "http://www.iconsfind.com/wp-content/uploads/2015/10/20151012_561baed03a54e.png", recommendation1))
        checkins?.add(CheckIn("2", "57e1bf5e933140dd117545bc", "reserva", null, "ice queen", "https://s-media-cache-ak0.pinimg.com/564x/25/d6/78/25d678f123cb4d392f3e2d66f1d342cc.jpg", recommendation2))
        checkins?.add(CheckIn("3", "57db1c91483bec7b0a70e7bc", "purty", "/img/caffe_luxe.jpg", "john bob", "https://cdn3.iconfinder.com/data/icons/glypho-generic-icons/64/user-woman-512.png", recommendation3))
    }

    fun  addCheckIn(checkin : CheckIn) : Observable<MutableList<CheckIn>>{

        //todo some work to add checkin
        //add checkin to local copy

        checkins?.add(checkin)
        return Observable.create { it.onNext(checkins) }
    }


    fun loadCheckIns(id : String) : Observable<List<CheckIn>> {

        var memory : Observable<List<CheckIn>> = loadRecommendationMemory()
        var disk : Observable<List<CheckIn>> = loadRecommendationsDisk()
        val full : Observable<List<CheckIn>> = Observable.concat(memory, disk).first { it != null }
        return full
    }


    private fun loadRecommendationMemory() : Observable<List<CheckIn>> {
        if(checkins == null)hydrateRecommendations()
        return Observable.create { it.onNext(checkins); it.onCompleted(); }
    }

    private fun loadRecommendationsDisk() : Observable<List<CheckIn>>{
        return Observable.just(listOf(CheckIn("4", "57e1bf5e933140dd117545bc", "cuvee", null, "john bob", "http://www.iconsfind.com/wp-content/uploads/2015/10/20151012_561baed03a54e.png")))
    }

    fun loadCheckInById(id : String) : CheckIn?{
        return checkins?.first { it?.id.equals(id) }
    }

}