package com.android.brewnotes.checkins

import android.util.Log
import com.android.brewnotes.service.CoffeeBagManager
import com.android.brewnotes.servicelayer.CoffeeBag
import javax.inject.Inject

/**
 * Created by jacobduron on 10/27/16.
 */
class AddCheckInPresenter @Inject constructor(val bagManager: CoffeeBagManager){

    var addRecView : AddRecView? = null


    fun attach(view : AddRecView){
        this.addRecView = view
    }

    fun detach(){
        this.addRecView = null
    }


    fun loadBagInfo(id: String?){
        var bag = bagManager.getBagById(id)
        addRecView?.displayBag(bag)

    }

    fun saveCheckIn(){
        var aromas = addRecView?.getAroma()
        var bodies = addRecView?.getBody()
        var flavors = addRecView?.getFlavor()
        var finishes = addRecView?.getFinsh()

        Log.d("app", "aromas: " + aromas!!.size)
        Log.d("app", "bodies: " + bodies!!.size)
        Log.d("app", "flavors: " + flavors!!.size)
        Log.d("app", "finishes: " + finishes!!.size)


    }




    public interface AddRecView{
        fun displayBag(bag:CoffeeBag)
        fun getScore() : Int
        fun getAroma() : List<String>
        fun getBody() : List<String>
        fun getFlavor() : List<String>
        fun getFinsh() : List<String>
        fun saveFinish()
        fun saveError()
    }
}