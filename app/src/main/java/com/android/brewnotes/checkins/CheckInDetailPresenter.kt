package com.android.brewnotes.checkins

import android.util.Log
import com.android.brewnotes.service.CheckInManager
import com.android.brewnotes.service.CoffeeManager
import com.android.brewnotes.service.UserManager
import com.android.brewnotes.servicelayer.CheckIn
import com.android.brewnotes.servicelayer.CoffeeBag
import com.android.brewnotes.servicelayer.Recommendation
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by jacobduron on 11/1/16.
 */
class CheckInDetailPresenter @Inject constructor(val checkInManager : CheckInManager, val userManager: UserManager, val bagManager: CoffeeManager) {

    var bag : CoffeeBag? = null

    var detailView : CheckInDetailView? = null


    fun attach(detailView :CheckInDetailView){
        this.detailView = detailView
    }

    fun detach(){
        this.detailView = null
    }

    fun loadBagInfo(id: String?){
        bag = bagManager.getBagById(id)
        detailView?.displayBag(bag)

    }

    fun loadCheckIn(id : String){
        if(id == null){
            detailView?.setUpAdditionView()
        }else{
            detailView?.setUpDetailView()
            var checkIn = checkInManager.loadCheckInById(id)
            detailView?.displayCheckin(checkIn)
        }

    }

    fun saveCheckIn(){
        if(detailView == null){
            return
        }
        var aromas = detailView?.getAroma()
        var bodies = detailView?.getBody()
        var flavors = detailView?.getFlavor()
        var finishes = detailView?.getFinsh()
        var comment = detailView?.getComment()
        var score = detailView!!.getScore()


        var checkIn = CheckIn("xx", bag?._id!!, bag?.name!!, null, userManager.user.fullName, userManager.user.photo.profilePhoto)
        var rec = Recommendation(comment, Date())
        rec.aromas = aromas
        rec.body = bodies
        rec.flavor = flavors
        rec.finish = finishes
        rec.overallScore = score

        checkIn.rec = rec

        checkInManager.addCheckIn(checkIn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d("TAG", "itsize: " + it?.size)
                            detailView?.saveFinish()},
                        {detailView?.saveError()},
                        {}
                )
    }




    public interface CheckInDetailView{
        fun displayCheckin(checkIn:CheckIn?)
        fun displayBag(bag:CoffeeBag?)
        fun getScore() : Int
        fun getAroma() : List<String>
        fun getBody() : List<String>
        fun getFlavor() : List<String>
        fun getFinsh() : List<String>
        fun getComment() : String
        fun saveFinish()
        fun saveError()

        fun setUpDetailView()
        fun setUpAdditionView()

    }
}