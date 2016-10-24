package com.android.brewnotes.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.brewnotes.R;
import com.android.brewnotes.servicelayer.Recommendation;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by jacobduron on 10/20/16.
 */

public class RecommendationContainer extends LinearLayout {


    public RecommendationContainer(Context context) {
        super(context);
    }

    public RecommendationContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecommendationContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void displayRecommendations(List<Recommendation> recs){
        for(int i = 0; i < 2; i++){
            View recRow = LayoutInflater.from(getContext()).inflate(R.layout.row_detail_recommendation, this, false);
            hydrateRow(recRow, recs.get(i));
            this.addView(recRow);
        }
        View button = LayoutInflater.from(getContext()).inflate(R.layout.see_more_button, this, true);
        if(recs.size() > 2){
            button.setVisibility(View.VISIBLE);
        }else{
            button.setVisibility(View.GONE);
        }
    }

    void hydrateRow(View row, Recommendation data){
        TextView recUserName = (TextView)row.findViewById(R.id.recommendation_user_name);
        TextView recUserComment = (TextView)row.findViewById(R.id.recommendation_comment);
        ImageView recUserProfile = (ImageView)row.findViewById(R.id.recommendation_user_picture);

        recUserName.setText(data.userName);
        recUserComment.setText(data.comment);

        if(data.userIconUrl != null){
            Glide.with(getContext())
                    .load(data.userIconUrl)
                    .into(recUserProfile);
        }

    }



}
