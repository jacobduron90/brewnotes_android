package com.android.brewnotes.service

import android.util.Log
import com.android.brewnotes.servicelayer.CoffeeBag
import com.android.brewnotes.servicelayer.CoffeeCompany
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import rx.Observable

/**
 * Created by jacobduron on 11/1/16.
 */
class CoffeeManager constructor(val userManager: UserManager, val contract : BrewNotesContract){

    var bagMap : MutableMap<String, List<CoffeeBag>> = mutableMapOf()
    internal var companies: List<CoffeeCompany>? = null

    fun getCoffeeBagListNetwork(companyId: String): Observable<List<CoffeeBag>> {
        Log.d("COffeeBagmanager", "authtoken: " + userManager.authToken)
        bagMap.put("blank", listOf());
        return contract.getCoffeeBagsRx(userManager.authToken, companyId)
                .doOnNext { coffeeBags -> bagMap.put(companyId, coffeeBags) }
    }

    fun getCoffeeBagListMem(id: String): Observable<List<CoffeeBag>>? {
        return Observable.create<List<CoffeeBag>> {
            it.onNext(bagMap.get(id))
            it.onCompleted()
        }
    }

    fun getCoffeeBagList(companyId: String) : Observable<List<CoffeeBag>>{
        return Observable.concat(getCoffeeBagListMem(companyId), getCoffeeBagListNetwork(companyId))
            .first { list -> list != null }
    }

    fun getCoffeeCompaniesRx(): Observable<List<CoffeeCompany>> {
        return contract.getCoffeeCompaniesRx(userManager.authToken)
    }

    fun getCoffeeCompanies(): Observable<List<CoffeeCompany>> {
        return Observable.concat(getCoffeeCompaniesMem(), getCoffeeCompaniesRx()).first { coffeeCompanies -> coffeeCompanies != null }
    }

    fun getCoffeeCompaniesMem(): Observable<List<CoffeeCompany>> {
        return Observable.create { subscriber ->
                subscriber.onNext(companies)
                subscriber.onCompleted()
        }
    }

    fun getBagById(id: String?): CoffeeBag? {
        Log.d("TAG", "search for: " + id + ". whole list, " + bagMap.containsKey("blank"))
        var bags = bagMap.flatMap { entry -> entry.value }

        var gson = GsonBuilder().create()
        Log.d("TAG", "does contain, " + gson.toJson(bags))
        try {
            return bagMap.flatMap { entry -> entry.value }.first{ bag -> bag._id.equals(id)}
        } catch(e: Exception) {
            return null
        }
    }

}