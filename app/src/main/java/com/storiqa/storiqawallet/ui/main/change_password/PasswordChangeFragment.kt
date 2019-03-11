package com.storiqa.storiqawallet.ui.main.change_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordChangeBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.ui.common.onSubmitButtonClicked

class PasswordChangeFragment : BaseFragment<FragmentPasswordChangeBinding, PasswordChangeViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_password_change

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<PasswordChangeViewModel> = PasswordChangeViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        (activity as IBaseActivity).setupActionBar(binding.toolbar, backButtonEnabled = true)

        initView()

        return view
    }

    private fun initView() {
        val context = context ?: return
        binding.tilPasswordRepeat.typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
        binding.tilPasswordNew.typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
        binding.tilPasswordCurrent.typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)

        binding.etPasswordRepeat.setOnEditorActionListener { textView, actionId, event ->
            onSubmitButtonClicked(textView, actionId, event) { viewModel.onConfirmButtonClicked() }
        }
        binding.etPasswordCurrent.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validatePasswords()
        }
        binding.etPasswordNew.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validatePasswords()
        }
        binding.etPasswordRepeat.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validatePasswords()
        }
    }
}