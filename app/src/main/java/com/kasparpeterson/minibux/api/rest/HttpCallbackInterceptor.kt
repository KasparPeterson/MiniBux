package com.kasparpeterson.minibux.api.rest

/**
 * Created by kaspar on 22/06/2017.
 */
class HttpCallbackInterceptor<in T>(val listener: HttpListener<T>,
                                    val interceptor: (T) -> Unit) : HttpListener<T> {

    override fun onResponse(response: T) {
        interceptor.invoke(response)
        listener.onResponse(response)
    }

    override fun onFailure() {
        listener.onFailure()
    }
}