package com.storiqa.storiqawallet.socialnetworks

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*
import javax.inject.Inject

class FacebookAuthHelper
@Inject
constructor() {

    private val callbackManager = CallbackManager.Factory.create()

    private val permissions = Arrays.asList("public_profile", "user_gender", "email")

    fun registerCallback(onSuccess: (token: String) -> Unit, onError: (FacebookException) -> Unit) {
        LoginManager.getInstance().loginBehavior = LoginBehavior.WEB_VIEW_ONLY
        LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        onSuccess(loginResult.accessToken.token)
                    }

                    override fun onCancel() {
                        Log.d("TAGGG", "onCancel")
                    }

                    override fun onError(exception: FacebookException) {
                        onError(exception)
                    }
                })
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun requestLogIn(activity: Activity) {
        LoginManager.getInstance().logInWithReadPermissions(activity, permissions)
    }
}