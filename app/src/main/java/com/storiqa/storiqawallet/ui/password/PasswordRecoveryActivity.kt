package com.storiqa.storiqawallet.ui.password

import android.net.Uri
import android.os.Bundle
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.ui.base.BaseFragmentActivity
import com.storiqa.storiqawallet.ui.password.reset.PasswordResetFragment
import com.storiqa.storiqawallet.ui.password.setup.PasswordSetupFragment

class PasswordRecoveryActivity : BaseFragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_password)

        val data: Uri? = intent?.data
        val token = data?.path?.split("/")?.last()

        if (token == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, PasswordResetFragment(), "PasswordResetFragment")
                    .commit()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, PasswordSetupFragment.newInstance(token), "PasswordSetupFragment")
                    .commit()
        }
    }
}