package com.example.wouldyoueat.apiRequest

import com.example.wouldyoueat.model.ImageModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FruitsImageInterface {
    @GET("/v1/search")
    fun getFruitImage(
        @Header("Authorization") apiKey: String,
        @Query("query") fruitName: String): Call<ImageModel>
}