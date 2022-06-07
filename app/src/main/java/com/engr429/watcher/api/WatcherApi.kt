package com.engr429.watcher.api

import com.engr429.watcher.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WatcherApi {

    @GET("list")
    fun getSceneKeys(): Call<List<String>>

    companion object {
        fun create() : WatcherApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_URL)
                .build()
            return retrofit.create(WatcherApi::class.java)

        }
    }
}