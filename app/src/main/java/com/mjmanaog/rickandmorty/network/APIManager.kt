package com.mjmanaog.rickandmorty.network

import com.mjmanaog.rickandmorty.network.model.Characters
import io.reactivex.Observable

/**
 * APIManager class is created to separate all the collection of api calls
 */
class APIManager{

    var apiService: APIService = APIClient().getRetrofitInstance()!!.create(APIService::class.java)

    fun getCharacters(): Observable<Characters> {
        return apiService.getCharacters()
    }
}