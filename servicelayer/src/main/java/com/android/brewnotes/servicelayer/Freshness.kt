package com.android.brewnotes.servicelayer

import org.joda.time.DateTime
import java.util.*

/**
 * Created by jacobduron on 10/18/16.
 */
interface Freshness {


    fun isFresh() : Boolean
    fun madeOn() : DateTime
    fun MAX_FRESH() : Long
}