package com.kasparpeterson.minibux.api.rest

import com.kasparpeterson.minibux.api.models.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by kaspar on 21/06/2017.
 */
interface ProductService {

    @GET("products")
    @Headers("Accept-Language: en-EN,en;q=0.8")
    fun getProducts(@Header("Authorization") authorization: String): Call<List<Product>>

    @GET("products/{securityId}")
    @Headers("Accept-Language: en-EN,en;q=0.8")
    fun getProduct(@Header("Authorization") authorization: String,
                   @Path("securityId") securityId: String): Call<Product>

}