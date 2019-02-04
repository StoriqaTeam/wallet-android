package com.storiqa.storiqawallet.ui.authorization

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityAuthorizationBinding
import com.storiqa.storiqawallet.extensions.setup
import com.storiqa.storiqawallet.ui.authorization.signin.SignInFragment
import com.storiqa.storiqawallet.ui.base.BaseActivity


class AuthorizationActivity : BaseActivity<ActivityAuthorizationBinding, AuthorizationViewModel, IAuthorizationNavigator>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_authorization

    override fun getViewModelClass(): Class<AuthorizationViewModel> = AuthorizationViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, SignInFragment(), "SignInFragment")
                    .commitNow()
        } else if (supportFragmentManager.findFragmentByTag("SignUpFragment") != null) {
            binding.tabLayout.getTabAt(1)?.select()
        }

        checkIntentData(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null)
            checkIntentData(intent)
    }

    private fun initView() {
        binding.tabLayout.setup()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                hideKeyboard()
                when (tab.text) {
                    resources.getString(R.string.tab_sign_in) -> viewModel.onSignInTabSelected()
                    resources.getString(R.string.tab_sign_up) -> viewModel.onSignUpTabSelected()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                hideKeyboard()
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                hideKeyboard()
            }
        })
    }

    private fun checkIntentData(intent: Intent) {
        if (intent.action == "com.storiqa.storiqawallet.SIGN_UP")
            binding.tabLayout.getTabAt(1)?.select()

        val path = intent.data?.path ?: return
        val token = path.split("/").last()

        if (path.contains("verify_email")) {
            viewModel.confirmEmail(token)
        } else if (path.contains("register_device")) {
            viewModel.confirmAttachDevice(token)
        }
    }

}