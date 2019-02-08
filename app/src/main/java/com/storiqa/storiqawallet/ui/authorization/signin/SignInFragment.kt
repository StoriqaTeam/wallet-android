package com.storiqa.storiqawallet.ui.authorization.signin

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentSignInBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.common.onSubmitButtonClicked

class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_sign_in

    override fun getViewModelClass(): Class<SignInViewModel> = SignInViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view.context)
    }

    private fun initView(context: Context) {
        binding.tilPassword.typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)

        binding.etPassword.setOnEditorActionListener { textView, actionId, event ->
            onSubmitButtonClicked(textView, actionId, event) { viewModel.onSubmitButtonClicked() }
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateEmail()
        }
    }

}