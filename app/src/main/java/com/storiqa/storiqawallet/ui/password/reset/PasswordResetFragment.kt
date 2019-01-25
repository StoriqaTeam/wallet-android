package com.storiqa.storiqawallet.ui.password.reset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordResetBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.ui.common.onSubmitButtonClicked

class PasswordResetFragment : BaseFragment<FragmentPasswordResetBinding, PasswordResetViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_password_reset

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<PasswordResetViewModel> = PasswordResetViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        (activity as IBaseActivity).setupActionBar(binding.toolbar, " ", true)

        initView()

        return view
    }

    private fun initView() {
        binding.etEmail.setOnEditorActionListener { textView, actionId, event ->
            onSubmitButtonClicked(textView, actionId, event) { viewModel.onPasswordResetButtonClicked() }
        }
    }

}