package com.android.brewnotes.dashboard

import android.util.Log
import com.android.brewnotes.User
import com.android.brewnotes.service.RecommendationManager
import com.android.brewnotes.service.UserManager
import com.android.brewnotes.servicelayer.Recommendation
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jacobduron on 10/24/16.
 */
class DashboardPresenter @Inject constructor(val userManager:UserManager, val recManager : RecommendationManager){

    var dashView : DashboardView? = null

    fun attach(dView : DashboardView){
        this.dashView = dView
    }


    fun detach(){
        this.dashView = null
    }

    fun loadData() {
        Log.d("hit", "hit succ");
        userManager.user
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { dashView?.displayUserInfo(it) }

        recManager.loadRecommendations("")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                    {

                        dashView?.displayRecInfo(it.toMutableList()) },
                    {dashView?.displayRecError()},
                    {}
            )

    }



    interface DashboardView{
        fun displayUserInfo(user : User?)
        fun displayRecInfo(recList : MutableList<Recommendation>)
        fun displayRecError()

    }



}