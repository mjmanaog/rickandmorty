package com.mjmanaog.rickandmorty.network

import com.mjmanaog.rickandmorty.network.model.Characters
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * APIService interface will include the needed endpoints
 */
interface APIService{
    @GET("character/")
    fun getCharacters(): Observable<Characters>
}