package com.android.brewnotes.coffeebag;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.brewnotes.R;
import com.android.brewnotes.framework.BaseActivity;
import com.android.brewnotes.service.UserManager;
import com.android.brewnotes.servicelayer.CoffeeBag;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

public class CoffeeBagDetailActivity extends BaseActivity {

    public static final String EXTRA_COFFEE_BAG_DETAIL = "extra_coffee_bag_detail";
    private View roastRow, countryRow;
    private TextView countryValue, roastValue;
    @Inject UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_bag_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CoffeeBag bag = (CoffeeBag)getIntent().getSerializableExtra(EXTRA_COFFEE_BAG_DETAIL);

        CollapsingToolbarLayout collapseBar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapseBar.setTitle(bag.name);



        TextView coffeeBagName = (TextView)findViewById(R.id.coffee_bag_company_name);
        roastRow = findViewById(R.id.roast_row);
        countryRow = findViewById(R.id.country_row);

        countryValue = (TextView)findViewById(R.id.coffee_detail_country);
        roastValue = (TextView)findViewById(R.id.coffee_detail_roast);

        ImageView heroPhoto = (ImageView)findViewById(R.id.coffee_detail_hero_photo);
        countryValue.setText(bag.countryOfOrigin);
        if(bag.roast != null){
            roastValue.setText(bag.roast);
        }else{
            roastRow.setVisibility(View.GONE);
        }


        coffeeBagName.setText(bag.name);
        if(bag.photo != null)
            Glide.with(this)
                .load(bag.photo.detailPhoto)
                .into(heroPhoto);
    }

}
