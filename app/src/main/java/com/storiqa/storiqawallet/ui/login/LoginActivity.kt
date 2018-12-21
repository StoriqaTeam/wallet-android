package com.storiqa.storiqawallet.ui.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityLoginBinding
import com.storiqa.storiqawallet.objects.GoogleAuthFlow
import com.storiqa.storiqawallet.ui.base.BaseActivity
import com.storiqa.storiqawallet.ui.common.onSubmitButtonClicked
import com.storiqa.storiqawallet.ui.password.reset.PasswordResetFragment


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private lateinit var googleAuthFlow: GoogleAuthFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        val data: Uri? = intent?.data
        val token = data?.path?.split("/")?.last()

        if (token != null) {
            viewModel.confirmEmail(token)
        }
    }

    private fun initView() {
        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateEmail()
        }

        binding.etPassword.setOnEditorActionListener { textView, actionId, event ->
            onSubmitButtonClicked(textView, actionId, event) { viewModel.onSubmitButtonClicked() }
        }
    }

    override fun subscribeEvents() {
        super.subscribeEvents()

        viewModel.requestLoginViaFacebook.observe(this, Observer { requestLoginViaFacebook() })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.facebookAuthHelper.onActivityResult(requestCode, resultCode, data)
    }

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_login

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    private fun requestLoginViaFacebook() {
        viewModel.facebookAuthHelper.requestLogIn(this)
    }
}