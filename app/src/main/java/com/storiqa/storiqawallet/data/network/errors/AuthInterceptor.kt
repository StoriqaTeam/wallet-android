package com.storiqa.storiqawallet.data.network.errors

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.storiqa.storiqawallet.provider.ITokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor
@Inject
constructor(private val tokenProvider: ITokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("Pessos", "dc dkcd").build()
        val response = chain.proceed(request)
        return proceedResponse(response, chain)
    }

    private fun proceedResponse(response: Response, chain: Interceptor.Chain): Response {
        when (response.code()) {
            200 -> return response
            400 -> throw BadRequest
            422 -> {
                if (response.body() == null)
                    throw UnknownError

                val gson = Gson()
                val type = object : TypeToken<HashMap<String, Array<ValidationError>>>() {}.type
                val validationErrors: HashMap<String, Array<ValidationError>> =
                        gson.fromJson(response.body()?.string(), type)

                if (validationErrors["token"]?.isNotEmpty() == true && validationErrors["token"]?.get(0)?.code == ErrorCode.EXPIRED) {
                    val token = tokenProvider.refreshToken()
                    if (token != null) {
                        val request = response.request().newBuilder()
                                .header("Authorization", "Bearer $token") // use the new access token
                                .build()
                        return chain.proceed(request)
                    } else
                        throw TokenExpired
                }
                throw UnprocessableEntity(validationErrors)

            }
            in 500..599 -> throw InternalServerError
            else -> throw UnknownError
        }
    }
}