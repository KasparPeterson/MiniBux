package com.kasparpeterson.minibux

import android.app.Application
import com.google.gson.Gson

import com.kasparpeterson.minibux.api.BuxWebSocketClient
import com.kasparpeterson.minibux.api.ProductManager
import com.kasparpeterson.minibux.api.ProductService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by kaspar on 13/06/2017.
 */
class MiniBux : Application() {

    private val BASE_URL = "https://api.beta.getbux.com/core/16/"

    companion object {
        val gson = Gson()
        lateinit var instance: MiniBux
            private set
    }

    lateinit var buxWebSocketClient: BuxWebSocketClient
    lateinit var productManager: ProductManager

    override fun onCreate() {
        super.onCreate()
        instance = this
        buxWebSocketClient = BuxWebSocketClient(gson, OkHttpClient())
        setupProductManager()
    }

    private fun setupProductManager() {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create<ProductService>(ProductService::class.java)
        productManager = ProductManager(service)
    }
}
