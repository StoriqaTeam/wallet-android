package com.storiqa.storiqawallet.network.errors

import com.storiqa.storiqawallet.R
import java.io.IOException

open class ErrorHandler {

    lateinit var showErrorDialog: (ErrorData) -> Unit
    lateinit var showErrorField: (ErrorCode, Int) -> Unit

    fun handleError(exception: Exception,
                    showErrorDialog: (ErrorData) -> Unit,
                    showErrorField: (ErrorCode, Int) -> Unit) {
        this.showErrorDialog = showErrorDialog
        this.showErrorField = showErrorField
        when (exception) {
            is BadRequest -> handleBadRequest()

            is InternalServerError -> handleInternalServerError()

            is UnknownError -> handleUnknownError()

            is UnprocessableEntity -> exception.validationErrors.forEach {
                it.value.forEach { handleUnprocessableEntity(it) }
            }

            is IOException -> handleNoInternetError()

            else -> handleUnknownError()
        }
    }

    fun handleBadRequest() {
        showErrorDialog(ErrorData(R.string.error_bad_request))
    }

    fun handleInternalServerError() {}

    fun handleUnknownError() {}

    fun handleNoInternetError() {}

    fun handleUnprocessableEntity(error: ValidationError) {
        when (error.code) {
            ErrorCode.EMAIL_NOT_VALID,
            ErrorCode.EMAIL_NOT_FOUND -> {
                if (error.message == "Invalid email format")
                    showErrorField(error.code, R.string.error_email_not_valid)
                else if (error.message == "Email not found")
                    showErrorField(error.code, R.string.error_email_not_exist)
            }
            ErrorCode.WRONG_PASSWORD ->
                showErrorField(error.code, R.string.error_password_wrong_pass)
            ErrorCode.DEVICE_NOT_ATTACHED ->
                showErrorField(error.code, R.string.error_device_not_attached)
        }
    }

}