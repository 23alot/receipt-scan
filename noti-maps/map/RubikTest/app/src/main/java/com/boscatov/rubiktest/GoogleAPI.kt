package com.boscatov.rubiktest

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import javax.xml.transform.Result


interface GoogleAPI {

    @GET("directions/json")
    fun getDirection(@Query("origin") origin: String,
                      @Query("destination") destination: String,
                      @Query("key") key: String,
                     @Query("mode") mode: String):
            Call<Data>
    /**
     * Factory class for convenient creation of the Api Service interface
     */
    companion object Factory {

        fun create(): GoogleAPI {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://maps.googleapis.com/maps/api/")
                    .build()

            return retrofit.create(GoogleAPI::class.java)
        }
    }
}