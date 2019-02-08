package com.storiqa.storiqawallet.ui.password.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordSetupBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
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

        (activity as IBaseActivity).setupActionBar(binding.toolbar, " ", true)

        viewModel.token = arguments?.getString(ARGUMENT_TOKEN, "") ?: ""

        initView()

        return view
    }

    private fun initView() {
        val context = context ?: return
        binding.tilPasswordRepeat.typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
        binding.tilPassword.typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)

        binding.etPasswordRepeat.setOnEditorActionListener { textView, actionId, event ->
            onSubmitButtonClicked(textView, actionId, event) { viewModel.onConfirmButtonClicked() }
        }
    }

}