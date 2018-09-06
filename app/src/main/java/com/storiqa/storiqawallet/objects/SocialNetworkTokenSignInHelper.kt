package com.storiqa.storiqawallet.objects

import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.storiqa.storiqawallet.constants.RequestCodes
import java.util.*

class SocialNetworkTokenSignInHelper(private val activity: AppCompatActivity) {

    private fun startSignInIntent(providers: MutableList<AuthUI.IdpConfig>, requestCode: Int) {
        activity.startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), requestCode)
    }

    fun startGoogleSignInProcess() {
        startSignInIntent(Arrays.asList(AuthUI.IdpConfig.GoogleBuilder().build()),
                RequestCodes().requestGoogleSignIn)
    }

    fun startFacebookSignInProcess() {
        startSignInIntent(Arrays.asList(AuthUI.IdpConfig.FacebookBuilder().build()),
                RequestCodes().requestFacebookSignIn)
    }


}