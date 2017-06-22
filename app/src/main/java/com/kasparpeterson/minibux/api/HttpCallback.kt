package com.kasparpeterson.minibux.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by kaspar on 21/06/2017.
 */
open class HttpCallback<T>(val listener: Listener<T>): Callback<T> {

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        if (response != null && response.isSuccessful && response.body() != null) {
            listener.onResponse(response.body()!!)
        } else {
            listener.onFailure()
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        listener.onFailure()
    }
}