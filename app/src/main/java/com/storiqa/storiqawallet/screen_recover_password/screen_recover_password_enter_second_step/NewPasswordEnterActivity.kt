package com.storiqa.storiqawallet.screen_recover_password.screen_recover_password_enter_second_step

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R

class NewPasswordEnterActivity : MvpAppCompatActivity(), NewPasswordEnterView {

    @InjectPresenter
    lateinit var presenter : NewPasswordEnterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password_enter)
    }
}
