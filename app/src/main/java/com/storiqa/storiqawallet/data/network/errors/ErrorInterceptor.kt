package com.storiqa.storiqawallet.data.network.errors

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
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

                if (validationErrors["token"]?.isNotEmpty() == true && validationErrors["token"]?.get(0)?.code == ErrorCode.EXPIRED)
                    throw TokenExpired

                throw UnprocessableEntity(validationErrors)
            }
            in 500..599 -> throw InternalServerError
            else -> throw UnknownError
        }
    }

}