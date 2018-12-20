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
        return ErrorPresenterDialog()
    }

    private fun handleBadRequest(): ErrorPresenterDialog {
        return ErrorPresenterDialog()
    }

    private fun handleInternalServerError(): ErrorPresenterDialog {
        return ErrorPresenterDialog()
    }

    private fun handleUnknownError(): ErrorPresenterDialog {
        return ErrorPresenterDialog()
    }

    private fun handleNoInternetError(): ErrorPresenterDialog {
        return ErrorPresenterDialog(
                DialogType.DEVICE_NOT_ATTACHED,
                R.string.error_no_internet_title,
                R.string.error_no_internet_description,
                R.drawable.general_error_icon)
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

                    ErrorCode.NOT_FOUND ->
                        errorField[field] = R.string.error_email_not_exist

                    ErrorCode.INVALID_PASSWORD ->
                        errorField[field] = R.string.error_password_wrong_pass

                    ErrorCode.NO_UPPER_CASE_CHARACTER ->
                        errorField[field] = R.string.error_password_no_upper

                    ErrorCode.INVALID_LENGTH ->
                        errorField[field] = R.string.error_password_invalid_length

                    ErrorCode.NO_NUMBER ->
                        errorField[field] = R.string.error_password_no_number

                    ErrorCode.ALREADY_EXISTS ->
                        return ErrorPresenterDialog(
                                DialogType.DEVICE_NOT_ATTACHED,
                                R.string.error_device_not_attached_title,
                                R.string.error_device_not_attached_description,
                                R.drawable.general_error_icon,
                                DialogButton(R.string.button_ok, {}),
                                DialogButton(R.string.cancel, {}))

                    ErrorCode.DEVICE_NOT_ATTACHED ->
                        return ErrorPresenterDialog(
                                DialogType.DEVICE_NOT_ATTACHED,
                                R.string.error_device_not_attached_title,
                                R.string.error_device_not_attached_description,
                                R.drawable.general_error_icon,
                                DialogButton(R.string.button_ok, {}),
                                DialogButton(R.string.cancel, {}))
                }

                errorFields.add(errorField)
            }
        }

        return ErrorPresenterFields(errorFields)
    }

}