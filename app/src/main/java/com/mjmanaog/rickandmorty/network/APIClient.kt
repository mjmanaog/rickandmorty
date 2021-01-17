package com.mjmanaog.rickandmorty.network

import com.mjmanaog.rickandmorty.helper.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * APIClient class
 */
class APIClient {
    private var retrofit: Retrofit? = null

    /**
     * @return the instance of the retrofit
     */
    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }
}