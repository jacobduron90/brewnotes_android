package com.android.brewnotes.checkins

import android.util.Log
import com.android.brewnotes.service.CheckInManager
import com.android.brewnotes.service.CoffeeBagManager
import com.android.brewnotes.service.UserManager
import com.android.brewnotes.servicelayer.CheckIn
import com.android.brewnotes.servicelayer.CoffeeBag
import com.android.brewnotes.servicelayer.Recommendation
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.Date
import javax.inject.Inject

/**
 * Created by jacobduron on 10/27/16.
 */
class AddCheckInPresenter @Inject constructor(val bagManager: CoffeeBagManager, val userManager: UserManager, val checkinManager : CheckInManager){

    var addRecView : AddRecView? = null
    var bag : CoffeeBag? = null


    fun attach(view : AddRecView){
        this.addRecView = view
    }

    fun detach(){
        this.addRecView = null
    }


    fun loadBagInfo(id: String?){
        bag = bagManager.getBagById(id)
        addRecView?.displayBag(bag)

    }

    fun saveCheckIn(){
        if(addRecView == null){
            return
        }
        var aromas = addRecView?.getAroma()
        var bodies = addRecView?.getBody()
        var flavors = addRecView?.getFlavor()
        var finishes = addRecView?.getFinsh()
        var comment = addRecView?.getComment()
        var score = addRecView!!.getScore()


        var checkIn = CheckIn(bag?._id!!, bag?.name!!, null, userManager.user.fullName, userManager.user.photo.profilePhoto)
        var rec = Recommendation(comment, Date())
        rec.aromas = aromas
        rec.body = bodies
        rec.flavor = flavors
        rec.finish = finishes
        rec.overallScore = score

        checkIn.rec = rec

        checkinManager.addCheckIn(checkIn)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { Log.d("TAG", "itsize: " + it?.size)
                        addRecView?.saveFinish()},
                    {addRecView?.saveError()},
                    {}
            )
    }




    public interface AddRecView{
        fun displayBag(bag:CoffeeBag?)
        fun getScore() : Int
        fun getAroma() : List<String>
        fun getBody() : List<String>
        fun getFlavor() : List<String>
        fun getFinsh() : List<String>
        fun getComment() : String
        fun saveFinish()
        fun saveError()
    }
}