package com.storiqa.storiqawallet.network

import com.storiqa.storiqawallet.network.requests.*
import com.storiqa.storiqawallet.network.responses.RegisterUserResponse
import com.storiqa.storiqawallet.network.responses.TokenResponse
import com.storiqa.storiqawallet.network.responses.UserInfoResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface WalletApi {

    @POST("v1/sessions")
    fun login(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body loginRequest: LoginRequest): Observable<TokenResponse>

    @POST("v1/sessions/oauth")
    fun loginByOauth(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body loginByOauth: LoginByOauthRequest): Observable<Response<Any>>

    @POST("v1/users")
    fun registerUser(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body registerUserRequest: RegisterUserRequest): Observable<RegisterUserResponse>

    @PUT("v1/users")
    fun updateUser(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Body updateUserRequest: UpdateUserRequest): Observable<Response<Any>>

    @POST("v1/users/confirm_email")
    fun confirmEmail(
            @Body confirmEmailRequest: ConfirmEmailRequest): Observable<Response<Any>>

    @POST("v1/users/add_device")
    fun addDevice(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body addDeviceRequest: AddDeviceRequest): Observable<Response<Any>>

    @POST("v1/users/confirm_add_device")
    fun confirmAddingDevice(
            @Body confirmAddingDeviceRequest: ConfirmAddingDeviceRequest): Observable<Response<Any>>

    @POST("v1/users/reset_password")
    fun resetPassword(
            @Body resetPasswordRequest: ResetPasswordRequest): Observable<Response<Any>>

    @POST("v1/users/resend_confirm_email")
    fun resendConfirmEmail(
            @Body resendConfirmEmailRequest: ResendConfirmEmailRequest): Observable<Response<Any>>

    @POST("v1/users/change_password")
    fun changePassword(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Body changePasswordRequest: ChangePasswordRequest): Observable<Response<Any>>

    @POST("v1/users/confirm_reset_password")
    fun confirmResetPassword(
            @Body confirmResetPasswordRequest: ConfirmResetPasswordRequest): Observable<TokenResponse>

    @GET("v1/users/me")
    fun getUserInfo(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<Response<UserInfoResponse>>

    @GET("v1/users/{userId}/accounts")
    fun getAccounts(
            @Path("userId") userId: Int,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int): Observable<Response<Any>>

    @PUT("v1/users/{userId}/accounts")
    fun updateAccounts(
            @Path("userId") userId: Int,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Body updateAccountRequest: UpdateAccountRequest): Observable<Response<Any>>

    @POST("v1/users/{userId}/accounts")
    fun createAccount(
            @Path("userId") userId: Int,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Body createAccountRequest: CreateAccountRequest): Observable<Response<Any>>

    @DELETE("v1/users/{userId}/accounts")
    fun deleteAccount(
            @Path("userId") userId: Int,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<Response<Any>>

    @GET("v1/accounts/{accountId}")
    fun getAccountInfo(
            @Path("userId") userId: Int,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<Response<Any>>

    @GET("v1/users/{userId}/transactions")
    fun getTransactions(
            @Path("userId") userId: Int,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int): Observable<Response<Any>>

    @POST("v1/transactions")
    fun createTransaction(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<Response<Any>>

    @GET("v1/rate")
    fun getExchangeRate(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<Response<Any>>

    @GET("v1/fees")
    fun calculateFee(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Body calculateFeeResponse: CalculateFeeResponse): Observable<Response<Any>>

}