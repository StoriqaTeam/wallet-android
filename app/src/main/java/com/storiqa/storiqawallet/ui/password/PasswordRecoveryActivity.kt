package com.storiqa.storiqawallet.ui.password

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.hideKeyboard
import com.storiqa.storiqawallet.ui.password.reset.PasswordResetFragment

class PasswordRecoveryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_password)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, PasswordResetFragment(), "PasswordResetFragment")
                .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        hideKeyboard()
        return super.onSupportNavigateUp()
    }
}