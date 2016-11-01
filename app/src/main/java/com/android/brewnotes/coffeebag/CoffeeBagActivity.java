package com.android.brewnotes.coffeebag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.android.brewnotes.service.CoffeeManager;
import com.android.brewnotes.ErrorHandler;
import com.android.brewnotes.R;
import com.android.brewnotes.framework.BaseActivity;
import com.android.brewnotes.servicelayer.CoffeeBag;
import com.android.brewnotes.servicelayer.CoffeeCompany;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeBagActivity extends BaseActivity implements CoffeeBagListPresenter.CoffeeBagListView{
    private final String TAG = "CoffeeBagActivity";


    @BindView(R.id.coffee_bag_list) RecyclerView coffeeBagList;
    @BindView(R.id.coffee_bag_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @Inject     CoffeeBagListPresenter presenter;
                CoffeeCompany company;
    private     CoffeeBagAdapter adapter;



    @Inject
    CoffeeManager coffeeBagManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attach(this);
        setContentView(R.layout.activity_coffee_bag);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        company = (CoffeeCompany)getIntent().getSerializableExtra("company");

        coffeeBagList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CoffeeBagAdapter();
        adapter.setClickListener(bagClickListener);
        coffeeBagList.setAdapter(adapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCoffeeBags(company._id);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.getCoffeeBags(company._id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    private CoffeeBagAdapter.BagClickListener bagClickListener = new CoffeeBagAdapter.BagClickListener() {
        @Override
        public void onBagClicked(CoffeeBag bag) {
            Intent detailIntent = new Intent(CoffeeBagActivity.this, CoffeeBagDetailActivity.class);
            detailIntent.putExtra(CoffeeBagDetailActivity.EXTRA_COFFEE_BAG_DETAIL, bag._id);
            startActivity(detailIntent);
        }
    };

    @Override
    public void displayBagList(@Nullable List<? extends CoffeeBag> bags) {
        adapter.addCoffeeBags((List<CoffeeBag>)bags);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displayErrorMessage(@NotNull Throwable t) {
        swipeRefreshLayout.setRefreshing(false);
        ErrorHandler.handleError(t, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
