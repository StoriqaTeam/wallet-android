package com.storiqa.storiqawallet.objects

import com.storiqa.storiqawallet.constants.JsonConstants
import com.storiqa.storiqawallet.network.network_responses.ErrorInfo
import org.json.JSONArray
import org.json.JSONObject

class ErrorRetriever(errors: List<ErrorInfo>) {

    var emailErrors = ""
    var passwordErrors = ""

    init {

        for (error in errors) {
            val detailMessageJson = JSONObject(error.data.details.message)

            if (detailMessageJson.has(JsonConstants().email)) {
                emailErrors = getErrors(detailMessageJson.getJSONArray(JsonConstants().email))
            }

            if (detailMessageJson.has(JsonConstants().password)) {
                passwordErrors = getErrors(detailMessageJson.getJSONArray(JsonConstants().password))
            }
        }
    }

    private fun getErrors(jsonArray: JSONArray): String {
        var result = ""
        for (i in 0 until jsonArray.length()) {
            result += jsonArray.getJSONObject(i).getString(JsonConstants().message) + "\n"
        }
        return result
    }

    fun isEmailErrorExist(): Boolean {
        return emailErrors.isNotEmpty()
    }

    fun isPasswordErrorExist(): Boolean {
        return passwordErrors.isNotEmpty()
    }
}