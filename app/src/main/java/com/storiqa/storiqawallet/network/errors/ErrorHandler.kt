package com.storiqa.storiqawallet.network.errors

import com.storiqa.storiqawallet.R
import java.io.IOException

open class ErrorHandler {

    lateinit var showErrorDialog: (ErrorPresenter) -> Unit
    lateinit var showErrorField: (ErrorPresenter, Int) -> Unit

    fun handleError(exception: Exception) -> ErrorPresenter {
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
        showErrorDialog(ErrorPresenter(R.string.error_bad_request))
    }

    fun handleInternalServerError() {}

    fun handleUnknownError() {}

    fun handleNoInternetError() {}

    fun handleUnprocessableEntity(error: ValidationError) {
        when (error.code) {
            ErrorCode.INVALID_EMAIL,
            ErrorCode.NOT_FOUND -> {
                if (error.message == "Invalid email format")
                    showErrorField(error.code, R.string.error_email_not_valid)
                else if (error.message == "Email not found")
                    showErrorField(error.code, R.string.error_email_not_exist)
            }
            ErrorCode.INVALID_PASSWORD ->
                showErrorField(error.code, R.string.error_password_wrong_pass)
            ErrorCode.ALREADY_EXISTS ->
                showErrorField(error.code, R.string.error_device_not_attached)
        }
    }

}