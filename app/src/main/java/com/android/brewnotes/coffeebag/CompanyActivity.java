package com.android.brewnotes.coffeebag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.brewnotes.framework.BaseActivity;
import com.android.brewnotes.servicelayer.CoffeeCompany;
import com.android.brewnotes.ErrorHandler;
import com.android.brewnotes.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyActivity extends BaseActivity implements CompanyAdapter.CompanyListener, CoffeeCompanyListPresenter.CoffeeCompanyListView {


    @BindView(R.id.coffee_companies_list)   RecyclerView companyList;
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void onCompanyClicked(CoffeeCompany company) {
        Intent companyIntent = new Intent(CompanyActivity.this, CoffeeBagActivity.class);
        companyIntent.putExtra("company", company);
        startActivity(companyIntent);
    }

    @Override
    public void setListOfBags(@NotNull List<? extends CoffeeCompany> bagList) {

        adapter.addCompanies((List<CoffeeCompany>)bagList);
    }

    @Override
    public void showErrorLoadingList(@NotNull Throwable t) {
        ErrorHandler.handleError(t, CompanyActivity.this);
    }
}
