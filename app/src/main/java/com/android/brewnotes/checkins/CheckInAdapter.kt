package com.android.brewnotes.checkins

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.android.brewnotes.R
import com.android.brewnotes.servicelayer.CheckIn
import com.android.brewnotes.servicelayer.Recommendation
import com.bumptech.glide.Glide
import javax.inject.Inject


/**
 * Created by jacobduron on 10/24/16.
 */
class CheckInAdapter @Inject constructor() : RecyclerView.Adapter<CheckInAdapter.RecHolder>(){



    var recList : MutableList<CheckIn>? = mutableListOf()


    fun setList(list : List<CheckIn>?){
        recList?.clear();
        if(list != null){
            recList?.addAll(list)
        }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if(recList == null){
            return 0
        }
        return recList!!.size
    }

    override fun onBindViewHolder(holder: RecHolder?, position: Int) {

        var checkIn = recList?.get(position)
        holder?.userName?.setText(checkIn?.userName)
        holder?.userComment?.setText(checkIn?.rec?.comment)
        if(checkIn?.userIcon != null){
            Glide.with(holder?.itemView?.context)
                .load(checkIn?.userIcon)
                .into(holder?.userPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecHolder {
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.row_detail_recommendation, parent, false)
        var holder = RecHolder(view)
        holder.init()
        return holder
    }

    class RecHolder(v : View) : RecyclerView.ViewHolder(v){

        @BindView(R.id.recommendation_user_name)    lateinit var userName : TextView
        @BindView(R.id.recommendation_comment)      lateinit var userComment : TextView
        @BindView(R.id.recommendation_user_picture) lateinit var userPhoto : ImageView

        fun init(){
            ButterKnife.bind(this, itemView)
        }


    }
}