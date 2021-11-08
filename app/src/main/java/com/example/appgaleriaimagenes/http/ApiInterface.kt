package com.example.appgaleriaimagenes.http

import com.example.appgaleriaimagenes.models.MessageResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("breed/hound/images/random/30")
    fun  getDogs() : Call<MessageResponse>
}