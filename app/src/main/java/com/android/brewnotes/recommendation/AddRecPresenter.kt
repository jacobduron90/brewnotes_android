package com.android.brewnotes.recommendation

import com.android.brewnotes.service.CoffeeBagManager
import com.android.brewnotes.servicelayer.CoffeeBag
import javax.inject.Inject

/**
 * Created by jacobduron on 10/27/16.
 */
class AddRecPresenter @Inject constructor(val bagManager: CoffeeBagManager){

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




    public interface AddRecView{
        fun displayBag(bag:CoffeeBag)
        fun getScore() : Int
    }
}