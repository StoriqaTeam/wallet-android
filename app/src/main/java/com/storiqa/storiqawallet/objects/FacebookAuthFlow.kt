package com.storiqa.storiqawallet.objects

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import java.util.*

class FacebookAuthFlow(private val activity : Activity, facebookButton: ImageView, val success: (token: String) -> Unit, val failure: () -> Unit) {

    val callbackManager = CallbackManager.Factory.create()

    init {
        val fbLoginManager = com.facebook.login.LoginManager.getInstance()
        fbLoginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                // here write code When Login successfully
                if (result.accessToken != null) {
                    success(result.accessToken.token!!)
                }
            }

            override fun onCancel() {}

            override fun onError(e: FacebookException) {failure()}
        })

        facebookButton.setOnClickListener {
            fbLoginManager.logInWithReadPermissions(activity, Arrays.asList("email", "public_profile", "user_gender"))
        }
    }

    fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}