package com.android.brewnotes.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.brewnotes.coffeebag.CoffeeCompanyListPresenter;
import com.android.brewnotes.framework.BaseActivity;
import com.android.brewnotes.coffeebag.CoffeeBagActivity;
import com.android.brewnotes.servicelayer.CoffeeCompany;
import com.android.brewnotes.ErrorHandler;
import com.android.brewnotes.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DashboardActivity extends BaseActivity implements CompanyAdapter.CompanyListener, CoffeeCompanyListPresenter.CoffeeCompanyListView {


    @Bind(R.id.coffee_companies_list)   RecyclerView companyList;
    @Inject     CompanyAdapter adapter;
    @Inject     CoffeeCompanyListPresenter presenter;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attach(this);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        companyList.setLayoutManager(new LinearLayoutManager(this));
        companyList.setAdapter(adapter);
        adapter.setCompanyListener(this);
    }

    @Override protected void onPostResume() {
        super.onPostResume();
        presenter.loadBags();
    }

    @Override
    public void onCompanyClicked(CoffeeCompany company) {
        Intent companyIntent = new Intent(DashboardActivity.this, CoffeeBagActivity.class);
        companyIntent.putExtra("company", company);
        startActivity(companyIntent);
    }

    @Override
    public void setListOfBags(@NotNull List<? extends CoffeeCompany> bagList) {

        adapter.addCompanies((List<CoffeeCompany>)bagList);
    }

    @Override
    public void showErrorLoadingList(@NotNull Throwable t) {
        ErrorHandler.handleError(t, DashboardActivity.this);
    }
}
