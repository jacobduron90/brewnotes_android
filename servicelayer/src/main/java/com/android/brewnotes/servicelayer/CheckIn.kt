package com.android.brewnotes.servicelayer

/**
 * Created by jacobduron on 10/28/16.
 */
data class CheckIn constructor(var bagId: String, var bagName:String, var photo: String?, var userName: String, var userIcon:String){

    var rec: Recommendation? = null



    constructor(bagId: String, bagName:String, photo: String?, userName: String, userIcon:String, rec: Recommendation) :
    this(bagId, bagName, photo, userName, userIcon){
        this.rec = rec
    }






}