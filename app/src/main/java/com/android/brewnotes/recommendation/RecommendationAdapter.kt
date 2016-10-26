package com.android.brewnotes.recommendation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.brewnotes.R
import com.android.brewnotes.servicelayer.Recommendation
import com.bumptech.glide.Glide
import javax.inject.Inject


/**
 * Created by jacobduron on 10/24/16.
 */
class RecommendationAdapter @Inject constructor() : RecyclerView.Adapter<RecommendationAdapter.RecHolder>(){



    var recList : MutableList<Recommendation>? = mutableListOf()


    fun setList(list : List<Recommendation>?){
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
        var rec = recList?.get(position)
        holder?.userName?.setText(rec?.userName)
        holder?.userComment?.setText(rec?.comment)
        if(rec?.userIconUrl != null){
            Glide.with(holder?.itemView?.context)
                .load(rec?.userIconUrl)
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

        lateinit var userName : TextView
        lateinit var userComment : TextView
        lateinit var userPhoto : ImageView

        fun init(){
            userName = itemView.findViewById(R.id.recommendation_user_name) as TextView
            userComment = itemView.findViewById(R.id.recommendation_comment) as TextView
            userPhoto = itemView.findViewById(R.id.recommendation_user_picture) as ImageView
        }


    }
}