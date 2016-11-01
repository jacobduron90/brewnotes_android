package com.android.brewnotes.dashboard

import android.os.Bundle
import android.support.design.widget.FloatingActionButton

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.android.brewnotes.ErrorHandler

import com.android.brewnotes.R
import com.android.brewnotes.servicelayer.User
import com.android.brewnotes.coffeebag.CompanyActivity
import com.android.brewnotes.framework.BaseActivity
import com.android.brewnotes.checkins.CheckInAdapter
import com.android.brewnotes.checkins.CheckInDetailActivity
import com.android.brewnotes.servicelayer.CheckIn
import com.android.brewnotes.widgets.ProfileCard
import javax.inject.Inject

class DashboardActivity : BaseActivity(), DashboardPresenter.DashboardView {


    @Inject lateinit  var dashPresenter : DashboardPresenter
    @Inject lateinit var recAdapter : CheckInAdapter
    lateinit var recList : RecyclerView
    lateinit var userCard : ProfileCard
    @BindView(R.id.fab) lateinit var fab : FloatingActionButton




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashPresenter.attach(this)

        setContentView(R.layout.activity_profile)
        ButterKnife.bind(this)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        recList = findViewById(R.id.recommendation_list) as RecyclerView
        recList.layoutManager = LinearLayoutManager(this)
        recList?.adapter = recAdapter
        userCard = findViewById(R.id.user_card) as ProfileCard
        fab.setOnClickListener { goToCoffeeSearch() }
        recAdapter.checkInClickListener = object : CheckInAdapter.CheckInClick{
            override fun checkInClicked(checkin: CheckIn?) {
                if(checkin != null)
                    startActivity(CheckInDetailActivity.getEditCheckInIntent(checkin?.id, checkin?.bagId, this@DashboardActivity))
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu)
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

    override fun displayRecInfo(recList: MutableList<CheckIn>) {
        recAdapter?.setList(recList)
    }


    override fun displayRecError() {
        Toast.makeText(this, "Error loading recommendation", Toast.LENGTH_SHORT).show()
    }

    fun goToCoffeeSearch() {
        startActivity(CompanyActivity.getCompanyActivityIntent(this));
    }
}
