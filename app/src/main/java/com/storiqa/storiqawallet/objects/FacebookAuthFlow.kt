package com.storiqa.storiqawallet.objects

import android.content.Intent
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import java.util.*

class FacebookAuthFlow(val facebookButton: LoginButton, val success: (token: String) -> Unit, val failure: () -> Unit) {

    val callbackManager = CallbackManager.Factory.create()

    init {
        facebookButton.setReadPermissions(Arrays.asList("email", "user_gender"))
        // Callback registration
        facebookButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (result != null && result.accessToken != null) {
                    success(result.accessToken.token!!)
                }
            }

            override fun onCancel() {
                Log.d("", "")
            }

            override fun onError(error: FacebookException?) {
                Log.d("", "")
            }
        })
    }

    fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}