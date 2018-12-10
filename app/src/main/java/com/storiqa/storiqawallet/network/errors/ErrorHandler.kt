package com.storiqa.storiqawallet.network.errors

import android.content.Context
import java.io.IOException
import javax.inject.Inject

class ErrorHandler
@Inject
constructor(private val context: Context) {

    fun handleError(exception: Exception, handleUnprocessableEntity: (ErrorCode) -> Unit) {
        when (exception) {
            is BadRequest -> TODO() //show dialog

            is InternalServerError -> TODO() //show dialog

            is UnknownError -> TODO() //show dialog

            is UnprocessableEntity ->
                exception.errors.forEach { handleUnprocessableEntity(it) }

            is IOException -> TODO() //show dialog

            else -> TODO() //show dialog
        }
    }

}