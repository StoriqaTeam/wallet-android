package com.storiqa.storiqawallet.utils

import android.util.Base64
import org.json.JSONObject


class JWTUtil {
    companion object {

        fun getUserId(jwt: String): String {
            val json = getJson(jwt)
            return json.getString("user_id")
        }

        fun getExpiredTime(jwt: String): String {
            val json = getJson(jwt)
            return json.getString("exp")
        }

        fun getProvider(jwt: String): String {
            val json = getJson(jwt)
            return json.getString("provider")
        }

        private fun getJson(jwt: String): JSONObject {
            val decodedBytes = Base64.decode(jwt.split(".")[1], Base64.URL_SAFE)
            return JSONObject(String(decodedBytes))
        }
    }
}