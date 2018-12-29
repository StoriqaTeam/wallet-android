package com.storiqa.storiqawallet.socialnetworks

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes.SIGN_IN_CANCELLED
import com.google.android.gms.common.api.ApiException
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import javax.inject.Inject


class GoogleAuthHelper
@Inject
constructor() {

    private val RC_SIGN_IN_CODE: Int = 42

    private lateinit var onSuccess: (token: String) -> Unit
    private lateinit var onError: (Exception) -> Unit

    fun registerCallback(onSuccess: (token: String) -> Unit, onError: (Exception) -> Unit) {
        this.onSuccess = onSuccess
        this.onError = onError
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == RC_SIGN_IN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val code = task.result?.serverAuthCode
            if (task.isSuccessful) {
                val token = task?.result?.idToken
                if (token != null) {
                    onSuccess(token)
                }
            } else {
                val exception = task.exception as ApiException?
                if (exception != null && exception.statusCode != SIGN_IN_CANCELLED)
                    onError(exception)
            }
            return true
        }
        return false
    }

    fun requestLogIn(activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestIdToken(App.res.getString(R.string.server_client_id))
                .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
        googleSignInClient.signOut()

        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN_CODE)
    }
}