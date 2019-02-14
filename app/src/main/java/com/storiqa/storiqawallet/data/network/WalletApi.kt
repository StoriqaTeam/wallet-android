package com.storiqa.storiqawallet.data.network

import com.storiqa.storiqawallet.data.network.requests.*
import com.storiqa.storiqawallet.data.network.responses.*
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
            @Body loginByOauth: LoginByOauthRequest): Observable<TokenResponse>

    @POST("v1/sessions/refresh")
    fun refreshToken(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<String>

    @POST("v1/sessions/oauth")
    fun revokeToken(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<TokenResponse>

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
            @Body confirmEmailRequest: ConfirmEmailRequest): Observable<String>

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
            @Body confirmResetPasswordRequest: ConfirmResetPasswordRequest): Observable<String>

    @GET("v1/users/me")
    fun getUserInfo(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<UserInfoResponse>

    @GET("v1/users/{id}/accounts")
    fun getAccounts(
            @Path("id") userId: Long,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Query("offset") offset: Long,
            @Query("limit") limit: Long): Observable<ArrayList<AccountResponse>>

    @PUT("v1/users/{id}/accounts")
    fun updateAccounts(
            @Path("id") userId: Long,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Body updateAccountRequest: UpdateAccountRequest): Observable<Response<Any>>

    @POST("v1/users/{id}/accounts")
    fun createAccount(
            @Path("id") userId: Long,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Body createAccountRequest: CreateAccountRequest): Observable<Response<Any>>

    @DELETE("v1/users/{id}/accounts")
    fun deleteAccount(
            @Path("id") userId: Long,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<Response<Any>>

    @GET("v1/accounts/{accountId}")
    fun getAccountInfo(
            @Path("id") userId: Long,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String): Observable<Response<Any>>

    @GET("v1/users/{id}/transactions")
    fun getTransactions(
            @Path("id") userId: Long,
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int): Observable<List<TransactionResponse>>

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

    @POST("v1/fees")
    fun calculateFee(
            @Header("Timestamp") timestamp: String,
            @Header("Device-id") deviceId: String,
            @Header("Sign") sign: String,
            @Header("Authorization") bearer: String,
            @Body feeRequest: FeeRequest): Observable<FeeResponse>

}