package com.boscatov.navigationdrawer.scan.QRRequests

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface QRApi {
    @GET("/receipts/check")
    fun check(@Query("fn") fNumber: String, @Query("i") fDocument: String,
              @Query("fp") fMemory: String, @Query("n") number: String,
              @Query("t") time: String, @Query("s") sum: String): Call<TestCheck>
    @GET("/receipts/get")
    fun get(@Query("fn") fNumber: String, @Query("i") fDocument: String,
              @Query("fp") fMemory: String, @Query("n") number: String,
              @Query("t") time: String, @Query("s") sum: String): Call<Check>

    @GET
    fun get(@Url info: String): Call<Check>
    @GET
    fun check(@Url info: String): Call<TestCheck>

    companion object Factory {
        fun create(): QRApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl("Add your own webserver")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(QRApi::class.java)
        }
    }
}