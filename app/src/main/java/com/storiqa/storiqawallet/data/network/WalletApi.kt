package com.storiqa.storiqawallet.data.network

import com.storiqa.storiqawallet.data.network.errors.RefreshRateResponse
import com.storiqa.storiqawallet.data.network.requests.*
import com.storiqa.storiqawallet.data.network.responses.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface WalletApi {

    @POST("v1/sessions")
    fun login(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body loginRequest: LoginRequest): Single<TokenResponse>

    @POST("v1/sessions/oauth")
    fun loginByOauth(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body loginByOauth: LoginByOauthRequest): Single<TokenResponse>

    @POST("v1/sessions/oauth")
    fun revokeToken(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String): Single<TokenResponse>

    @POST("v1/users")
    fun registerUser(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body registerUserRequest: RegisterUserRequest): Single<RegisterUserResponse>

    @PUT("v1/users")
    fun updateUser(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body updateUserRequest: UpdateUserRequest): Single<UserInfoResponse>

    @POST("v1/users/confirm_email")
    fun confirmEmail(
            @Body confirmEmailRequest: ConfirmEmailRequest): Single<String>

    @POST("v1/users/add_device")
    fun addDevice(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body addDeviceRequest: AddDeviceRequest): Single<Response<Any>>

    @POST("v1/users/confirm_add_device")
    fun confirmAddingDevice(
            @Body confirmAddingDeviceRequest: ConfirmAddingDeviceRequest): Single<Response<Any>>

    @POST("v1/users/reset_password")
    fun resetPassword(
            @Body resetPasswordRequest: ResetPasswordRequest): Single<Response<Any>>

    @POST("v1/users/resend_confirm_email")
    fun resendConfirmEmail(
            @Body resendConfirmEmailRequest: ResendConfirmEmailRequest): Single<Response<Any>>

    @POST("v1/users/change_password")
    fun changePassword(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body changePasswordRequest: ChangePasswordRequest): Single<String>

    @POST("v1/users/confirm_reset_password")
    fun confirmResetPassword(
            @Body confirmResetPasswordRequest: ConfirmResetPasswordRequest): Single<String>

    @GET("v1/users/me")
    fun getUserInfo(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String): Single<UserInfoResponse>

    @GET("v1/users/{id}/accounts")
    fun getAccounts(
            @Path("id") userId: Long,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Query("offset") offset: Long,
            @Query("limit") limit: Long): Single<ArrayList<AccountResponse>>

    @GET("v1/users/{id}/transactions")
    fun getTransactions(
            @Path("id") userId: Long,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int): Single<List<TransactionResponse>>

    @POST("v1/transactions")
    fun createTransaction(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body createTransactionRequest: CreateTransactionRequest): Single<TransactionResponse>

    @POST("v1/rate")
    fun getExchangeRate(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body exchangeRateRequest: ExchangeRateRequest): Single<ExchangeRateResponse>

    @POST("v1/rate/refresh")
    fun refreshRate(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body refreshRateRequest: RefreshRateRequest): Single<RefreshRateResponse>

    @POST("v1/fees")
    fun calculateFee(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Body feeRequest: FeeRequest): Single<FeeResponse>

}