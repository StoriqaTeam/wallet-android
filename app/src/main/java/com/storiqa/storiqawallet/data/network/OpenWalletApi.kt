package com.storiqa.storiqawallet.data.network

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenWalletApi {

    @POST("v1/sessions/refresh")
    fun refreshToken(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Call<String>
}