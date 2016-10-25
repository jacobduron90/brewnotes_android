package com.android.brewnotes.widgets;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.brewnotes.R;
import com.android.brewnotes.User;
import com.bumptech.glide.Glide;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jacobduron on 10/25/16.
 */

public class ProfileCard extends CardView{



    @Bind(R.id.profile_photo)           ImageView profilePhoto;
    @Bind(R.id.user_card_name)          TextView userName;
    @Bind(R.id.user_checkin_counter)    TextView checkinCounter;
    @Bind(R.id.user_following_counter)  TextView followingCounter;

    public ProfileCard(Context context) {
        super(context);
        init();
    }

    public ProfileCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }


    private void init(){
        inflateLayout();
    }

    private void inflateLayout(){
        LayoutInflater.from(getContext()).inflate(R.layout.card_profile_section, this, true);
    }

    public void setData(User user){
        userName.setText(user.firstName + " " + user.lastName);
        if(user.photo.profilePhoto != null){
            Glide.with(getContext())
                    .load(user.photo.profilePhoto)
                    .into(profilePhoto);
        }

        checkinCounter.setText(String.format("%d\nCHECK-INs", user.checkInCount));
        followingCounter.setText(String.format("%d\nFOLLOWING", user.followingCount));





    }
}
