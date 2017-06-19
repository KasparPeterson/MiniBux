package com.kasparpeterson.minibux

import android.app.Application
import com.google.gson.Gson

import com.kasparpeterson.minibux.api.BuxWebClient
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

    lateinit var okHttpClient: OkHttpClient
    lateinit var buxWebClient: BuxWebClient
    lateinit var productService: ProductService

    override fun onCreate() {
        super.onCreate()
        instance = this
        buxWebClient = BuxWebClient(gson)
        productService = ProductService(gson, OkHttpClient())
    }
}
