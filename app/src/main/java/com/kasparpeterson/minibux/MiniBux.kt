package com.kasparpeterson.minibux

import android.app.Application
import com.google.gson.Gson

import com.kasparpeterson.minibux.api.BuxWebSocketClient
import com.kasparpeterson.minibux.api.ProductService
import okhttp3.OkHttpClient

/**
 * Created by kaspar on 13/06/2017.
 */

class MiniBux : Application() {

    companion object {
        val gson = Gson()
        lateinit var instance: MiniBux
            private set
    }

    lateinit var buxWebSocketClient: BuxWebSocketClient
    lateinit var productService: ProductService

    override fun onCreate() {
        super.onCreate()
        instance = this
        buxWebSocketClient = BuxWebSocketClient(gson, OkHttpClient())
        productService = ProductService(gson, OkHttpClient())
    }
}
