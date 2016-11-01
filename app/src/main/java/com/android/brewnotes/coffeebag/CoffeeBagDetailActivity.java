package com.android.brewnotes.coffeebag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.brewnotes.R;
import com.android.brewnotes.checkins.CheckInDetailActivity;
import com.android.brewnotes.framework.BaseActivity;
import com.android.brewnotes.checkins.CheckInSummaryActivity;
import com.android.brewnotes.servicelayer.CheckIn;
import com.android.brewnotes.servicelayer.CoffeeBag;
import com.android.brewnotes.widgets.CheckInContainer;
import com.bumptech.glide.Glide;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoffeeBagDetailActivity extends BaseActivity implements CoffeeDetailPresenter.CoffeeDetailView{

    public static final String EXTRA_COFFEE_BAG_DETAIL = "extra_coffee_bag_detail";

    @BindView(R.id.roast_row)                   View roastRow;
    @BindView(R.id.country_row)                 View countryRow;
    @BindView(R.id.coffee_bag_company_name)     TextView companyNameValue;
    @BindView(R.id.coffee_detail_country)       TextView countryValue;
    @BindView(R.id.coffee_detail_roast)         TextView roastValue;
    @BindView(R.id.coffee_detail_hero_photo)    ImageView heroPhoto;
    @BindView(R.id.collapsing_toolbar)          CollapsingToolbarLayout collapseBar;
    @BindView(R.id.recommendation_container)
    CheckInContainer container;



    @Inject CoffeeDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attach(this);
        setContentView(R.layout.activity_coffee_bag_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        presenter.getBagById(getIntent().getStringExtra(EXTRA_COFFEE_BAG_DETAIL));
    }

    @Override
    protected void onPostResume() {
        Log.d("TAG", "onPostCalled");
        super.onPostResume();
        presenter.loadRecommendations();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void setBagDetails(CoffeeBag bag) {
        collapseBar.setTitle(bag.name);
        countryValue.setText(bag.countryOfOrigin);
        companyNameValue.setText(bag.name);
        if(bag.roast != null){
            roastValue.setText(bag.roast);
        }else{
            roastRow.setVisibility(View.GONE);
        }

        if(bag.photo != null)
            Glide.with(this)
                    .load(bag.photo.detailPhoto)
                    .into(heroPhoto);
    }

    @Override
    public void errorLoadingBag() {
        Toast.makeText(this, "Couldn't find bag", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCheckIns(@org.jetbrains.annotations.Nullable List<CheckIn> recs) {
        if(recs != null){
            container.displayCheckIns(recs);
            Toast.makeText(this,"recs size: " + recs.size(), Toast.LENGTH_SHORT).show();
            findViewById(R.id.recommendation_see_more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(CheckInSummaryActivity.Companion.getIntent(CoffeeBagDetailActivity.this, presenter.getBag()._id));
                }
            });
        }
    }

    @OnClick(R.id.add_rec_button)
    public void goToAddRec(){
        startActivity(CheckInDetailActivity.Companion.getAddCheckInIntent(this, presenter.getBag()._id));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
