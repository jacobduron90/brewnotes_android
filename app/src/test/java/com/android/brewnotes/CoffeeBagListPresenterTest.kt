package com.android.brewnotes

import com.android.brewnotes.coffeebag.CoffeeBagListPresenter
import com.android.brewnotes.service.CoffeeManager
import com.android.brewnotes.servicelayer.CoffeeBag
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import rx.Observable



/**
 * Created by jacobduron on 10/17/16.
 */
class CoffeeBagListPresenterTest {


    var mockCoffeeManager : CoffeeManager? = null;
    var presenter : CoffeeBagListPresenter? = null;
    var mockView : CoffeeBagListPresenter.CoffeeBagListView? = null;

    @Before fun setup(){
        RxSchedulerHelper.setup();

        mockCoffeeManager = Mockito.mock(CoffeeManager::class.java)
        mockView = Mockito.mock(CoffeeBagListPresenter.CoffeeBagListView::class.java)

        presenter = CoffeeBagListPresenter(mockCoffeeManager!!)

    }

    @After fun teardown(){
        RxSchedulerHelper.teardown()
    }

    @Test fun presenterCallsShowListOnView() {
        presenter?.attach(mockView!!)
        val bags : MutableList<CoffeeBag> = mutableListOf()
        var observable : Observable<MutableList<CoffeeBag>> = Observable.just(bags);

        Mockito.`when`(mockCoffeeManager?.getCoffeeBagList(Mockito.anyString())).thenReturn(observable);

        presenter?.getCoffeeBags("1");

        Mockito.verify(mockView!!, Mockito.times(1)).displayBagList(Mockito.anyListOf(CoffeeBag::class.java))
    }

    @Test fun presenterShouldCallShowError() {
        presenter?.attach(mockView!!)
        val missedException : Exception = Exception("messed up")
        var errorObservable : Observable<MutableList<CoffeeBag>> = Observable.error(missedException)
        Mockito.`when`(mockCoffeeManager?.getCoffeeBagList(Mockito.anyString())).thenReturn(errorObservable)

        presenter?.getCoffeeBags("1")
        Mockito.verify(mockView!!, Mockito.times(1)).displayErrorMessage(missedException)


    }
}