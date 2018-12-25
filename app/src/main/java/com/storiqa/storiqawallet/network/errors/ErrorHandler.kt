package com.storiqa.storiqawallet.network.errors

import com.facebook.FacebookException
import com.storiqa.storiqawallet.R
import java.io.IOException

open class ErrorHandler {

    fun handleError(exception: Exception): ErrorPresenter {
        when (exception) {
            is FacebookException ->
                return handleFacebookAuthError()

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

    private fun handleFacebookAuthError(): ErrorPresenter {
        return UnknownErrorDialogPresenter()
    }

    private fun handleBadRequest(): ErrorPresenterDialog {
        return UnknownErrorDialogPresenter()
    }

    private fun handleInternalServerError(): ErrorPresenterDialog {
        return UnknownErrorDialogPresenter()
    }

    private fun handleUnknownError(): ErrorPresenterDialog {
        return UnknownErrorDialogPresenter()
    }

    private fun handleNoInternetError(): ErrorPresenterDialog {
        return NoInternetDialogPresenter()
    }

    private fun handleUnprocessableEntity(validationErrors: HashMap<String,
            Array<ValidationError>>): ErrorPresenter {
        val errorFields = ArrayList<HashMap<String, Int>>()

        for ((field, errors) in validationErrors) {
            for (error in errors) {
                val errorField = HashMap<String, Int>()
                when (error.code) {
                    ErrorCode.INVALID_EMAIL ->
                        errorField[field] = R.string.error_email_not_valid

                    ErrorCode.NOT_EXISTS ->
                        if (field == "email")
                            errorField[field] = R.string.error_email_not_exist
                        else if (field == "device")
                            return NotAttachedDialogPresenter().apply { params = error.params }

                    /////////delete/////////

                    /*ErrorCode.NOT_EXIST ->
                            errorField[field] = R.string.error_email_not_exist

                    ErrorCode.DEVICE_NOT_ATTACHED ->
                            return NotAttachedDialogPresenter().apply { params = error.params }*/

                    ////////////////////////

                    ErrorCode.INVALID_PASSWORD ->
                        errorField[field] = R.string.error_password_wrong_pass

                    ErrorCode.NO_UPPER_CASE_CHARACTER ->
                        errorField[field] = R.string.error_password_no_upper

                    ErrorCode.INVALID_LENGTH ->
                        errorField[field] = R.string.error_password_invalid_length

                    ErrorCode.NO_NUMBER ->
                        errorField[field] = R.string.error_password_no_number

                    ErrorCode.EMAIL_TIMEOUT ->
                        return EmailTimeoutDialogPresenter()

                    ErrorCode.NOT_VERIFIED ->
                        return EmailNotVerifiedDialogPresenter()
                }

                errorFields.add(errorField)
            }
        }

        return ErrorPresenterFields(errorFields)
    }

}