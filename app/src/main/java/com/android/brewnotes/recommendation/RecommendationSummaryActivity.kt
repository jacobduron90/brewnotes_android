package com.android.brewnotes.recommendation

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import butterknife.ButterKnife
import com.android.brewnotes.R
import com.android.brewnotes.framework.BaseActivity
import com.android.brewnotes.servicelayer.Recommendation
import javax.inject.Inject

class RecommendationSummaryActivity : RecommendationSummaryPresenter.RecommenationSummaryView, BaseActivity(){

    @Inject lateinit var adapter : RecommendationAdapter;
    @Inject lateinit var presenter : RecommendationSummaryPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter?.attach(this)
        setContentView(R.layout.activity_recommendation_summary)
        ButterKnife.bind(this)

        var listView = findViewById(R.id.recommendation_summary_list) as RecyclerView
        listView?.adapter = adapter;
        listView?.layoutManager = LinearLayoutManager(this)
    }

    override fun onPostResume() {
        super.onPostResume()
        presenter?.loadRecs("someString")
    }

    override fun onDestroy() {
        presenter?.detach()
        super.onDestroy()
    }

    override fun loadRecs(list: List<Recommendation>?) {
        adapter?.setList(list)
    }

    companion object{
        fun getIntent(context : Context, id : String) : Intent {
            var intent = Intent(context, RecommendationSummaryActivity::class.java)
            intent.putExtra("bag_id", id)
            return intent
        }
    }
}
