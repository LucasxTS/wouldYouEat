package com.example.wouldyoueat.apiRequest

import com.example.wouldyoueat.model.Fruits
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("api/fruit/all")
    fun getFruits() : Call<List<Fruits>>

}