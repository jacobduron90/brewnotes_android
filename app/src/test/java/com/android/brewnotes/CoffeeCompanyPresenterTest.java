package com.android.brewnotes;

import com.android.brewnotes.coffeebag.CoffeeBagListPresenter;
import com.android.brewnotes.coffeebag.CoffeeCompanyListPresenter;
import com.android.brewnotes.service.CoffeeBagManager;
import com.android.brewnotes.servicelayer.CoffeeCompany;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jacobduron on 10/16/16.
 */

public class CoffeeCompanyPresenterTest {

    CoffeeCompanyListPresenter.CoffeeCompanyListView mockCompanyView;
    CoffeeBagManager mockBagManager;
    CoffeeCompanyListPresenter presenter;
    Exception newException;

    @Before public void setup(){
        RxSchedulerHelper.setup();
        mockCompanyView = mock(CoffeeCompanyListPresenter.CoffeeCompanyListView.class);
        mockBagManager = mock(CoffeeBagManager.class);
        newException = new Exception("messed up");


        presenter = new CoffeeCompanyListPresenter(mockBagManager);
        presenter.attach(mockCompanyView);
    }

    @After public void teardown(){
        RxSchedulerHelper.teardown();
    }


    @Test public void testSetBagListCalledOnSuccessfulList(){
        List<CoffeeCompany> companyList = new ArrayList<>();
        Observable<List<CoffeeCompany>> companyObservable = Observable.just(companyList);
        when(mockBagManager.getCoffeeCompanies()).thenReturn(companyObservable);

        presenter.loadBags();

        verify(mockCompanyView).setListOfBags(anyList());
    }

    @Test public void testShowErrorMessageCalledOnError(){

        Observable<List<CoffeeCompany>> observable = Observable.error(newException);
        when(mockBagManager.getCoffeeCompanies()).thenReturn(observable);
        presenter.loadBags();
        verify(mockCompanyView).showErrorLoadingList(newException);
    }
}
