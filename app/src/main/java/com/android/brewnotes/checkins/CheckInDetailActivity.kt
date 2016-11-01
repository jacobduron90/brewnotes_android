package com.android.brewnotes.checkins

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

import com.android.brewnotes.R
import com.android.brewnotes.framework.BaseActivity
import com.android.brewnotes.servicelayer.CheckIn
import com.android.brewnotes.servicelayer.CoffeeBag
import com.android.brewnotes.widgets.MultiSelectionCard
import javax.inject.Inject

class CheckInDetailActivity : BaseActivity(), CheckInDetailPresenter.CheckInDetailView{

    @BindView(R.id.aroma_card)                  lateinit var aromaCard : MultiSelectionCard
    @BindView(R.id.body_card)                   lateinit var bodyCard : MultiSelectionCard
    @BindView(R.id.flavor_card)                 lateinit var flavorCard : MultiSelectionCard
    @BindView(R.id.finish_card)                 lateinit var finishCard : MultiSelectionCard
    @BindView(R.id.checkin_rating)              lateinit var rating : SeekBar
    @BindView(R.id.checkin_comment_written)     lateinit var commentLabel : TextView
    @BindView(R.id.recommendation_user_name)    lateinit var userName : TextView
    @BindView(R.id.recommendation_user_photo)   lateinit var userPhoto : ImageView
    @BindView(R.id.coffee_bag_name)             lateinit var bagName : TextView
    @BindView(R.id.coffee_bag_company_name)     lateinit var companyName : TextView
    @BindView(R.id.checkin_score_label)         lateinit var scoreLabel : TextView
    @BindView(R.id.checkin_comment)             lateinit var comment : TextView

    @Inject lateinit var detailPresenter : CheckInDetailPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailPresenter?.attach(this)
        setContentView(R.layout.activity_add_recommendation)
        ButterKnife.bind(this)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }



    override fun onPostResume() {
        super.onPostResume()
        detailPresenter?.loadCheckIn(intent.getStringExtra("rec_id"))
        detailPresenter?.loadBagInfo(intent.getStringExtra("bag_id"))

    }

    override fun onDestroy() {
        super.onDestroy()
        detailPresenter?.detach()
    }



    override fun displayCheckin(checkIn: CheckIn?) {
        if(checkIn == null){
            return;
        }
        supportActionBar?.setTitle(checkIn?.bagName)


        aromaCard.loadFromCheckIn(checkIn);
        bodyCard.loadFromCheckIn(checkIn);
        flavorCard.loadFromCheckIn(checkIn);
        finishCard.loadFromCheckIn(checkIn);

        rating.setProgress(checkIn?.rec?.overallScore?:0)
        rating.isEnabled = false

        userName?.text = checkIn?.userName
        scoreLabel?.text = checkIn?.rec?.overallScore.toString()



        commentLabel.setText("\"" + checkIn?.rec?.comment + "\"")

    }

    override fun setUpDetailView() {
        findViewById(R.id.add_checkin_button).visibility = View.GONE
        comment.visibility = View.GONE
        commentLabel.visibility = View.VISIBLE
    }

    override fun setUpAdditionView() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object{
        fun getEditCheckInIntent(recId : String, bagId: String, ctx: Context) : Intent {

            var launchIntent = Intent(ctx, CheckInDetailActivity::class.java)
            launchIntent.putExtra("rec_id", recId)
            launchIntent.putExtra("bag_id",  bagId)
            return launchIntent
        }

        fun getAddCheckInIntent(context : Context, id : String) : Intent {
            var launchIntent = Intent(context, CheckInDetailActivity::class.java)
            launchIntent.putExtra("bag_id", id)
            return launchIntent
        }
    }

    override fun getComment(): String {
        return comment.text.toString()
    }

    override fun getScore(): Int {
        return rating?.progress
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
        detailPresenter?.saveCheckIn()
    }

    override fun saveFinish() {
        finish()
    }

    override fun saveError() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayBag(bag: CoffeeBag?) {
        bagName?.text = bag?.name
        companyName?.text = "by " + bag?.companyName
    }


}
