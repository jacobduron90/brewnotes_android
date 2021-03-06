package com.android.brewnotes

import com.android.brewnotes.coffeebag.CoffeeDetailPresenter
import com.android.brewnotes.service.CoffeeManager
import com.android.brewnotes.service.CheckInManager
import com.android.brewnotes.servicelayer.CoffeeBag
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Created by jacobduron on 10/17/16.
 */
class CoffeeBagDetailTest {


    var mockCoffeeManager : CoffeeManager? = null
    var mockRecommendationManager : CheckInManager? = null
    var mockDetailView : CoffeeDetailPresenter.CoffeeDetailView? = null
    var presenter : CoffeeDetailPresenter? = null

    @Before fun setup() {
        RxSchedulerHelper.setup()
        mockCoffeeManager = Mockito.mock(CoffeeManager::class.java)
        mockDetailView = Mockito.mock(CoffeeDetailPresenter.CoffeeDetailView::class.java)
        mockRecommendationManager = Mockito.mock(CheckInManager::class.java)
        presenter = CoffeeDetailPresenter(mockCoffeeManager!!, mockRecommendationManager!!)
    }

    @Test fun testDisplayListCalledOnValidBag() {
        Mockito.`when`(mockCoffeeManager?.getBagById(Mockito.anyString())).thenReturn(CoffeeBag())
        presenter?.attach(mockDetailView!!)
        presenter?.getBagById("123")
        Mockito.verify(mockCoffeeManager!!, Mockito.times(1)).getBagById(Mockito.anyString())
        Mockito.verify(mockDetailView!!, Mockito.times(1)).setBagDetails(Mockito.any(CoffeeBag::class.java))
    }

    @Test fun testDisplayErrorOnException() {
        Mockito.`when`(mockCoffeeManager?.getBagById(Mockito.anyString())).thenReturn(null)
        presenter?.attach(mockDetailView!!)
        presenter?.getBagById("123")
        Mockito.verify(mockDetailView!!, Mockito.times(1)).errorLoadingBag()
    }
}