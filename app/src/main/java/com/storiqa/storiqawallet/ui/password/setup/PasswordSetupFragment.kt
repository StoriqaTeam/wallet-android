package com.storiqa.storiqawallet.ui.password.setup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordSetupBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.common.onSubmitButtonClicked

class PasswordSetupFragment : BaseFragment<FragmentPasswordSetupBinding, PasswordSetupViewModel>() {


    companion object {
        private const val ARGUMENT_TOKEN = "argument_token"

        @JvmStatic
        fun newInstance(token: String): PasswordSetupFragment {
            val bundle = Bundle().apply {
                putString(ARGUMENT_TOKEN, token)
            }
            val passwordSetupFragment = PasswordSetupFragment()
            passwordSetupFragment.arguments = bundle
            return passwordSetupFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_password_setup

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<PasswordSetupViewModel> = PasswordSetupViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.token = arguments?.getString(ARGUMENT_TOKEN, "") ?: ""

        initView()

        return view
    }

    private fun initView() {
        binding.etPasswordRepeat.setOnEditorActionListener { textView, actionId, event ->
            onSubmitButtonClicked(textView, actionId, event) { viewModel.onConfirmButtonClicked() }
        }
    }

}