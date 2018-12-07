package com.storiqa.storiqawallet.network.errors

import android.content.Context
import java.io.IOException
import javax.inject.Inject

class ErrorHandler
@Inject
constructor(private val context: Context) {

    fun handleError(exception: Exception, handleUnprocessableEntity: (ErrorCode) -> Unit) {
        when (exception) {
            is BadRequest -> {
            }
            is InternalServerError -> {
            }
            is UnknownError -> {
            }
            is UnprocessableEntity -> {
                exception.errors.forEach { handleUnprocessableEntity(it) }
            }
            is IOException -> {
            }
            else -> {
            }
        }
    }

}