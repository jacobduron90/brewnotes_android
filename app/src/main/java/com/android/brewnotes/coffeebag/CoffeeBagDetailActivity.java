package com.android.brewnotes.coffeebag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.brewnotes.R;
import com.android.brewnotes.framework.BaseActivity;
import com.android.brewnotes.service.UserManager;
import com.android.brewnotes.servicelayer.CoffeeBag;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoffeeBagDetailActivity extends BaseActivity implements CoffeeDetailPresenter.CoffeeDetailView{

    public static final String EXTRA_COFFEE_BAG_DETAIL = "extra_coffee_bag_detail";

    @Bind(R.id.roast_row)                   View roastRow;
    @Bind(R.id.country_row)                 View countryRow;
    @Bind(R.id.coffee_bag_company_name)     TextView companyNameValue;
    @Bind(R.id.coffee_detail_country)       TextView countryValue;
    @Bind(R.id.coffee_detail_roast)         TextView roastValue;
    @Bind(R.id.coffee_detail_hero_photo)    ImageView heroPhoto;
    @Bind(R.id.collapsing_toolbar)          CollapsingToolbarLayout collapseBar;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
