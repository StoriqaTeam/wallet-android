package com.storiqa.storiqawallet.network.errors

import com.storiqa.storiqawallet.R
import java.io.IOException

open class ErrorHandler {

    fun handleError(exception: Exception): ErrorPresenter {
        when (exception) {
            is BadRequest ->
                return handleBadRequest()

            is InternalServerError ->
                return handleInternalServerError()

            is UnknownError ->
                return handleUnknownError()

            is UnprocessableEntity ->
                return handleUnprocessableEntity(exception.validationErrors)

            is IOException ->
                return handleNoInternetError()

            else ->
                return handleUnknownError()
        }
    }

    fun handleBadRequest(): ErrorPresenterDialog {
        return ErrorPresenterDialog()
    }

    fun handleInternalServerError(): ErrorPresenterDialog {
        return ErrorPresenterDialog()
    }

    fun handleUnknownError(): ErrorPresenterDialog {
        return ErrorPresenterDialog()
    }

    fun handleNoInternetError(): ErrorPresenterDialog {
        return ErrorPresenterDialog()
    }

    fun handleUnprocessableEntity(validationErrors: HashMap<String, Array<ValidationError>>): ErrorPresenter {
        val errorFields = HashMap<String, Int>()

        for ((field, errors) in validationErrors) {
            for (error in errors) {
                when (error.code) {
                    ErrorCode.INVALID_EMAIL ->
                        errorFields[field] = R.string.error_email_not_valid

                    ErrorCode.NOT_FOUND ->
                        errorFields[field] = R.string.error_email_not_exist

                    ErrorCode.INVALID_PASSWORD ->
                        errorFields[field] = R.string.error_password_wrong_pass

                    ErrorCode.ALREADY_EXISTS -> TODO()

                    ErrorCode.DEVICE_NOT_ATTACHED ->
                        return ErrorPresenterDialog(R.string.error_device_not_attached_title,
                                R.string.error_device_not_attached_description,
                                R.drawable.general_error_icon,
                                DialogButton(R.string.button_ok, {}),
                                DialogButton(R.string.cancel, {}))
                }
            }
        }

        return ErrorPresenterFields(errorFields)
    }

}