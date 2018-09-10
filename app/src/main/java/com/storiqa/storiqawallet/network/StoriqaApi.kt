package com.storiqa.storiqawallet.network

import com.storiqa.storiqawallet.network.network_requests.*
import com.storiqa.storiqawallet.network.network_responses.RegisterUserResponse
import com.storiqa.storiqawallet.network.network_responses.GetTokenResponseByEmail
import com.storiqa.storiqawallet.network.network_responses.GetTokenResponseByProvider
import com.storiqa.storiqawallet.network.network_responses.ResetPasswordResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface StoriqaApi {

    @POST("/graphql")
    fun getTokenByEmailAndPassword(@Body getTokenByEmailRequest : GetTokenByEmailRequest) : Observable<GetTokenResponseByEmail>

    @POST("/graphql")
    fun registerUser(@Body registerUserRequest : RegisterUserRequest) : Observable<RegisterUserResponse>

    @POST("/graphql")
    fun getStoriqaTokenFromFirebaseToken(@Body getStoriqaTokenFromFirebaseTokenRequest: GetStoriqaTokenFromFirebaseTokenRequest) : Observable<GetTokenResponseByProvider>

    @POST("/graphql")
    fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest) : Observable<ResetPasswordResponse>

    @POST("/graphql")
    fun applyNewPassword(@Body applyNewPasswordRequest: ApplyNewPasswordRequest) : Observable<ResetPasswordResponse>

    class Factory {
        private val baseUrl = "https://stage.stq.cloud:60443" //stage

        fun getInstance(): StoriqaApi {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build();

            val retrofit = Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
            return retrofit.create(StoriqaApi::class.java)
        }
    }
}