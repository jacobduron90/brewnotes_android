package com.android.brewnotes.checkins

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

import com.android.brewnotes.R
import com.android.brewnotes.framework.BaseActivity
import com.android.brewnotes.servicelayer.CoffeeBag
import com.android.brewnotes.widgets.MultiSelectionCard
import javax.inject.Inject

class AddCheckInActivity : BaseActivity(), AddCheckInPresenter.AddRecView {



    @Inject lateinit var presenter : AddCheckInPresenter

    @BindView(R.id.aroma_card)  lateinit var aromaCard : MultiSelectionCard
    @BindView(R.id.body_card)   lateinit var bodyCard : MultiSelectionCard
    @BindView(R.id.flavor_card) lateinit var flavorCard : MultiSelectionCard
    @BindView(R.id.finish_card) lateinit var finishCard : MultiSelectionCard
    @BindView(R.id.toolbar)     lateinit var bar : Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter?.attach(this)
        setContentView(R.layout.activity_add_recommendation)
        ButterKnife.bind(this)

        setSupportActionBar(bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter?.loadBagInfo(intent.getStringExtra("bag_id"))
    }


    companion object{
        fun getLaunchIntent(context : Context, id : String) : Intent {
            var launchIntent = Intent(context, AddCheckInActivity::class.java)
            launchIntent.putExtra("bag_id", id)
            return launchIntent
        }
    }

    override fun displayBag(bag: CoffeeBag) {
        bar.setTitle(bag.name)
    }

    override fun getScore(): Int {
        return 0
    }

    override fun getAroma(): List<String> {
        return aromaCard?.selections
    }

    override fun getBody(): List<String> {
        return bodyCard?.selections
    }

    override fun getFlavor(): List<String> {
        return flavorCard?.selections
    }

    override fun getFinsh(): List<String> {
        return finishCard?.selections
    }

    @OnClick(R.id.add_checkin_button)
    fun saveCheckIn(){
        presenter?.saveCheckIn()
    }

    override fun saveFinish() {
        finish()
    }

    override fun saveError() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
