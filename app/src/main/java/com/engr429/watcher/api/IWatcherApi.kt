package com.engr429.watcher.api

import com.engr429.watcher.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IWatcherApi {

    @GET("list")
    fun getSceneKeys(): Call<List<String>>

    @POST("status")
    fun sendUpdate(@Body update: WatcherUpdate): Call<Blank>

    companion object {
        fun create() : IWatcherApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_URL)
                .build()
            return retrofit.create(IWatcherApi::class.java)

        }
    }
}