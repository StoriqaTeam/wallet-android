package com.storiqa.storiqawallet.data.network.errors

import com.facebook.FacebookException
import com.storiqa.storiqawallet.App
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

            is TokenExpired ->
                return handleTokenExpired()

            else ->
                return handleUnknownError()
        }
    }

    private fun handleFacebookAuthError(): ErrorPresenter {
        return UnknownErrorDialogPresenter()
    }

    private fun handleBadRequest(): MessageDialogPresenter {
        return UnknownErrorDialogPresenter()
    }

    private fun handleInternalServerError(): MessageDialogPresenter {
        return UnknownErrorDialogPresenter()
    }

    private fun handleUnknownError(): MessageDialogPresenter {
        return UnknownErrorDialogPresenter()
    }

    private fun handleNoInternetError(): MessageDialogPresenter {
        return NoInternetDialogPresenter()
    }

    private fun handleTokenExpired(): MessageDialogPresenter {
        return TokenExpiredDialogPresenter()
    }

    private fun handleUnprocessableEntity(validationErrors: HashMap<String,
            Array<ValidationError>>): ErrorPresenter {
        val errorFields = ArrayList<HashMap<String, String>>()

        for ((field, errors) in validationErrors) {
            for (error in errors) {
                val errorField = HashMap<String, String>()
                when (error.code) {
                    ErrorCode.INVALID_EMAIL ->
                        errorField[field] = App.res.getString(R.string.error_email_not_valid)

                    ErrorCode.BLOCKED ->
                        errorField[field] = App.res.getString(R.string.error_email_blocked)

                    ErrorCode.ALREADY_EXISTS ->
                        errorField[field] = App.res.getString(R.string.error_email_exists)

                    ErrorCode.NOT_EXISTS -> {
                        if (field == "email")
                            errorField[field] = App.res.getString(R.string.error_email_not_exist)
                        else if (field == "device")
                            return NotAttachedDialogPresenter().apply { params = error.params }
                    }

                    ErrorCode.NOT_PROVIDED ->
                        return EmailNotProvidedDialogPresenter()

                    ErrorCode.INVALID_PASSWORD ->
                        errorField[field] = App.res.getString(R.string.error_password_wrong_pass)

                    ErrorCode.NO_UPPER_CASE_CHARACTER ->
                        errorField[field] = App.res.getString(R.string.error_password_no_upper)

                    ErrorCode.INVALID_LENGTH ->
                        errorField[field] = App.res.getString(R.string.error_password_invalid_length)

                    ErrorCode.NO_NUMBER ->
                        errorField[field] = App.res.getString(R.string.error_password_no_number)

                    ErrorCode.EMAIL_TIMEOUT ->
                        return EmailTimeoutDialogPresenter()

                    ErrorCode.NOT_VERIFIED ->
                        return EmailNotVerifiedDialogPresenter()

                    ErrorCode.WRONG_DEVICE_ID ->
                        return WrongDeviceIdDialogPresenter()

                    ErrorCode.DIFFERENT_CURRENCY ->
                        errorField[field] = App.res.getString(R.string.error_different_address)

                    ErrorCode.EXPIRED -> TODO()

                    ErrorCode.NOT_ENOUGH_ON_MARKET ->
                        errorField[field] = App.res.getString(R.string.error_not_enough_money_on_market)

                    ErrorCode.NOT_FOUND_EXCHANGE_RATE ->
                        errorField[field] = App.res.getString(R.string.error_not_found_exchange_rate)

                    ErrorCode.EXCHANGE_WRONG_LIMIT -> {
                        val currency = error.params?.get("currency")?.toUpperCase()
                        val minLimit = error.params?.get("min")
                        val maxLimit = error.params?.get("max")
                        errorField[field] = App.res.getString(
                                R.string.error_exchange_not_in_range,
                                "$minLimit $currency",
                                "$maxLimit $currency")
                    }

                    ErrorCode.NOT_ENOUGH_BALANCE -> {
                        errorField[field] = App.res.getString(R.string.error_not_enough_balance)
                    }

                    ErrorCode.TOKEN_REVOKED -> TODO() //TODO go to login screen
                }

                errorFields.add(errorField)
            }
        }

        return ErrorPresenterFields(errorFields)
    }

}