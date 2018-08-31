package com.storiqa.storiqawallet.objects

import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI

class SocialNetworkTokenSignInHelper(private val activity: AppCompatActivity) {

    fun startSignInIntent(providers: MutableList<AuthUI.IdpConfig>, requestCode: Int) {
        activity.startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), requestCode)
    }
}