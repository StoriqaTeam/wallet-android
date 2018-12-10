package com.storiqa.storiqawallet.network.errors

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.storiqa.storiqawallet.R

sealed class ApiError : Exception()

object BadRequest : ApiError()

object InternalServerError : ApiError()

object UnknownError : ApiError()

class UnprocessableEntity(private val validationErrors: HashMap<String, Array<ValidationError>?>) : ApiError() {

    var errors = ArrayList<ErrorCode>()

    init {
        for ((key, errorsArray) in validationErrors) {
            if (errorsArray != null)
                qualifyError(key, errorsArray)
        }
    }

    private fun qualifyError(key: String, errorsArray: Array<ValidationError>) {
        errorsArray.forEach {
            if (key == "password")
                when (it.code) {
                    "password" -> errors.add(ErrorCode.WRONG_PASSWORD)
                    "exists" -> errors.add(ErrorCode.WRONG_PASSWORD)
                }

            if (key == "email")
                when (it.message) {
                    "Invalid email format" -> errors.add(ErrorCode.EMAIL_NOT_VALID)
                    "Email not found" -> errors.add(ErrorCode.EMAIL_NOT_EXIST)
                }

            if (key == "device")
                if (it.message == "exists")
                    errors.add(ErrorCode.DEVICE_NOT_ATTACHED)
        }
    }
}

enum class ErrorCode(
        @StringRes val title: Int,
        @StringRes val description: Int? = null,
        @DrawableRes val image: Int? = null) {

    EMAIL_NOT_VALID(R.string.error_email_not_valid),
    EMAIL_NOT_EXIST(R.string.error_email_not_exist),
    WRONG_PASSWORD(R.string.error_password_wrong_pass),
    DEVICE_NOT_ATTACHED(R.string.error_device_not_attached),
    BAD_REQUEST(R.string.error_bad_request),
    SERVER_ERROR(R.string.error_server_error),
    NO_INTERNET(R.string.error_no_internet),
    UNKNOWN_ERROR(R.string.error_unknown_error)

}