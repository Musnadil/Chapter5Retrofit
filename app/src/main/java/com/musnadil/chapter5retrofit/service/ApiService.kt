package com.musnadil.chapter5retrofit.service

import com.musnadil.chapter5retrofit.model.GetAllCarResponseItem
import com.musnadil.chapter5retrofit.model.PostRegisterResponse
import com.musnadil.chapter5retrofit.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("admin/car")
    fun getAllCar() : Call<List<GetAllCarResponseItem>>

    @POST("admin/auth/register")
    fun postRegister(@Body request: RegisterRequest): Call<PostRegisterResponse>
}