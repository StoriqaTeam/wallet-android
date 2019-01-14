package com.storiqa.storiqawallet.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityLoginBinding
import com.storiqa.storiqawallet.ui.base.BaseActivity
import com.storiqa.storiqawallet.ui.common.onSubmitButtonClicked


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    val RC_SIGN_IN: Int = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        checkIntentData(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkIntentData(intent)
    }

    private fun checkIntentData(intent: Intent?) {
        val path = intent?.data?.path ?: return
        val token = path.split("/").last()

        if (path.contains("verify_email")) {
            viewModel.confirmEmail(token)
        } else if (path.contains("register_device")) {
            viewModel.confirmAttachDevice(token)
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

        viewModel.requestLoginViaGoogle.observe(this, Observer { requestLoginViaGoogle() })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        hideKeyboard()

        if (viewModel.facebookAuthHelper.onActivityResult(requestCode, resultCode, data))
            return

        if (viewModel.googleAuthHelper.onActivityResult(requestCode, resultCode, data))
            return
    }

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_login

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    private fun requestLoginViaFacebook() {
        viewModel.facebookAuthHelper.requestLogIn(this)
    }

    private fun requestLoginViaGoogle() {
        viewModel.googleAuthHelper.requestLogIn(this)
    }
}