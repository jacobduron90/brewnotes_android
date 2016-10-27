package com.android.brewnotes.recommendation

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.android.brewnotes.R
import com.android.brewnotes.framework.BaseActivity
import com.android.brewnotes.servicelayer.CoffeeBag
import javax.inject.Inject

class AddRecommendationActivity : BaseActivity(), AddRecPresenter.AddRecView {



    @Inject lateinit var presenter : AddRecPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter?.attach(this)
        setContentView(R.layout.activity_add_recommendation)
        presenter?.loadBagInfo(intent.getStringExtra("bag_id"))
    }


    companion object{
        fun getLaunchIntent(context : Context, id : String) : Intent {
            var launchIntent = Intent(context, AddRecommendationActivity::class.java)
            launchIntent.putExtra("bag_id", id)
            return launchIntent
        }
    }

    override fun displayBag(bag: CoffeeBag) {

    }

    override fun getScore(): Int {
        return 0
    }
}
