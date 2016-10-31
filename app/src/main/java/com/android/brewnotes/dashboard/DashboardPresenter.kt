package com.android.brewnotes.dashboard

import android.util.Log
import com.android.brewnotes.servicelayer.User
import com.android.brewnotes.service.CheckInManager
import com.android.brewnotes.service.UserManager
import com.android.brewnotes.servicelayer.CheckIn
import com.android.brewnotes.servicelayer.Recommendation
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by jacobduron on 10/24/16.
 */
class DashboardPresenter @Inject constructor(val userManager:UserManager, val recManager : CheckInManager){

    var dashView : DashboardView? = null

    fun attach(dView : DashboardView){
        this.dashView = dView
    }


    fun detach(){
        this.dashView = null
    }

    fun loadData() {
        Log.d("hit", "hit succ");
        dashView?.displayUserInfo(userManager.user)

        recManager.loadCheckIns("")
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
        fun displayRecInfo(recList : MutableList<CheckIn>)
        fun displayRecError()

    }



}