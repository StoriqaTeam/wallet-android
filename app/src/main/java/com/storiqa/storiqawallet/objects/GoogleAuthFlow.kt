package com.storiqa.storiqawallet.objects

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.storiqa.storiqawallet.constants.RequestCodes
import com.storiqa.storiqawallet.db.AuthPreferences

class GoogleAuthFlow(val activity: Activity, val success: (token: String) -> Unit, val failure : () -> Unit) {

    lateinit var authPreferences: AuthPreferences
    lateinit var accountManager: AccountManager

    private val SCOPE = "https://www.googleapis.com/auth/googletalk"

    fun performLogin() {
        accountManager = AccountManager.get(activity)
        authPreferences = AuthPreferences(activity)
        if (authPreferences.user != null && authPreferences.token != null) {
            success(authPreferences.token!!)
        } else {
            chooseAccount()
        }
    }


    private fun chooseAccount() {
        // use https://github.com/frakbot/Android-AccountChooser for
        // compatibility with older devices
        val intent = AccountManager.newChooseAccountIntent(null, null,
                arrayOf("com.google"), false, null, null, null, null)
        activity.startActivityForResult(intent, RequestCodes().accountGoogle)
    }

    private fun requestToken() {
        var userAccount: Account
        val user = authPreferences?.user
        for (account in accountManager!!.getAccountsByType("com.google")) {
            if (account.name.equals(user)) {
                userAccount = account;
                accountManager.getAuthToken(userAccount, "oauth2:" + SCOPE, null, activity,
                        OnTokenAcquired(), null);
                break;
            }
        }
    }

    private fun invalidateToken() {
        val accountManager = AccountManager.get(activity)
        accountManager.invalidateAuthToken("com.google",
                authPreferences.token)

        authPreferences.token = null
    }

    private inner class OnTokenAcquired : AccountManagerCallback<Bundle> {

        override fun run(result: AccountManagerFuture<Bundle>) {
            try {
                val bundle = result.result

                if (bundle.get(AccountManager.KEY_INTENT) != null) {
                    activity.startActivityForResult(bundle.get(AccountManager.KEY_INTENT) as Intent, RequestCodes().authorizationGoogle)
                } else {
                    val token = bundle.getString(AccountManager.KEY_AUTHTOKEN)
                    authPreferences.token = token
                    success(token)
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
    }

    fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCodes().authorizationGoogle) {
                requestToken()
            } else if (requestCode == RequestCodes().accountGoogle) {
                val accountName = data!!
                        .getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                authPreferences.user = accountName

                // invalidate old tokens which might be cached. we want a fresh
                // one, which is guaranteed to work
                invalidateToken()
                requestToken()
            }
        }
    }
}