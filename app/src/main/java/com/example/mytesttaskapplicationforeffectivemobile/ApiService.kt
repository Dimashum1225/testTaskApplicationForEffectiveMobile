package com.example.mytesttaskapplicationforeffectivemobile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import models.OfferResponse

interface ApiService {
    @GET("u/0/uc")
    fun getOffers(
        @Query("id") id: String,
        @Query("export") export: String
    ): Call<OfferResponse>
}
