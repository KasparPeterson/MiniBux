package com.kasparpeterson.minibux.api.rest

/**
 * Created by kaspar on 22/06/2017.
 */
interface HttpListener<in T> {
    fun onResponse(response: T)
    fun onFailure()
}