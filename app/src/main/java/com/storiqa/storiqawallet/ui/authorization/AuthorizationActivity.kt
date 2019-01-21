package com.storiqa.storiqawallet.ui.authorization

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityAuthorizationBinding
import com.storiqa.storiqawallet.ui.authorization.signin.SignInFragment
import com.storiqa.storiqawallet.ui.base.BaseActivity


class AuthorizationActivity : BaseActivity<ActivityAuthorizationBinding, AuthorizationViewModel>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_authorization

    override fun getViewModelClass(): Class<AuthorizationViewModel> = AuthorizationViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, SignInFragment(), "SignInFragment")
                .commitNow()

        checkIntentData(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null)
            checkIntentData(intent)
    }

    private fun initView() {
        val tabs = binding.tabLayout
        for (i in 0 until tabs.tabCount) {
            val tab = tabs.getTabAt(i)
            if (tab != null) {
                setupTab(tab, i)
            }
            val layout = (tabs.getChildAt(0) as LinearLayout).getChildAt(i) as LinearLayout
            val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layout.layoutParams = layoutParams
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                hideKeyboard()
                when (tab.text) {
                    resources.getString(R.string.tab_sign_in) -> viewModel.onSignInTabSelected()
                    resources.getString(R.string.tab_sign_up) -> viewModel.onSignUpTabSelected()
                }

                val tabTextView = tab.customView as TextView?
                if (tabTextView != null)
                    onTabSelected(tabTextView)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                hideKeyboard()
                val tabTextView = tab.customView as TextView?
                if (tabTextView != null)
                    onTabUnselected(tabTextView)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                hideKeyboard()
            }
        })
    }

    private fun setupTab(tab: TabLayout.Tab, pos: Int) {
        val tabTextView = TextView(this)
        tab.customView = tabTextView
        tabTextView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        tabTextView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        tabTextView.setPadding(0, 0, 0, 20)
        tabTextView.text = tab.text
        tabTextView.gravity = Gravity.BOTTOM

        if (pos == 0)
            onTabSelected(tabTextView)
        else
            onTabUnselected(tabTextView)
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

    private fun onTabSelected(textView: TextView) {
        textView.setTypeface(null, Typeface.BOLD)
        textView.textSize = 24f
        textView.setTextColor(ContextCompat.getColor(this, R.color.tab_selected_text))
    }

    private fun onTabUnselected(textView: TextView) {
        textView.setTypeface(null, Typeface.NORMAL)
        textView.textSize = 16f
        textView.setTextColor(ContextCompat.getColor(this, R.color.tab_unselected_text))
    }

}