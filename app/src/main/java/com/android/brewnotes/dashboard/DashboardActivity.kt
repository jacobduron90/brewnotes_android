package com.android.brewnotes.dashboard

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import com.android.brewnotes.ErrorHandler

import com.android.brewnotes.R
import com.android.brewnotes.User
import com.android.brewnotes.framework.BaseActivity
import com.android.brewnotes.recommendation.RecommendationAdapter
import com.android.brewnotes.servicelayer.Recommendation
import com.android.brewnotes.widgets.ProfileCard
import javax.inject.Inject

class DashboardActivity : BaseActivity(), DashboardPresenter.DashboardView {


    @Inject lateinit  var dashPresenter : DashboardPresenter
    @Inject lateinit var recAdapter : RecommendationAdapter
    lateinit var recList : RecyclerView
    lateinit var userCard : ProfileCard



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashPresenter.attach(this)

        setContentView(R.layout.activity_profile)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        recList = findViewById(R.id.recommendation_list) as RecyclerView
        recList.layoutManager = LinearLayoutManager(this)
        recList?.adapter = recAdapter
        userCard = findViewById(R.id.user_card) as ProfileCard
    }

    override fun onResume() {
        super.onResume()
        dashPresenter?.loadData()
    }

    override fun displayUserInfo(user: User?) {
        if(user == null){
            ErrorHandler.bounceToLogin(this)
            return;
        }
        userCard?.setData(user)
    }

    override fun displayRecInfo(recList: MutableList<Recommendation>) {
        recAdapter?.setList(recList)
    }


    override fun displayRecError() {
        Toast.makeText(this, "Error loading recommendation", Toast.LENGTH_SHORT).show()
    }
}
