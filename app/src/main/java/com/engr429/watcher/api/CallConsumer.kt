package com.engr429.watcher.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

object CallConsumer {
    fun <T> consume(call: Call<T>, onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful)
                    response.body()?.let { data -> onSuccess.invoke(data) }
                else
                    onFailure.invoke(Exception("${response.code()} - ${response.errorBody()}"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure.invoke(t)
            }

        })
    }
}