package com.storiqa.storiqawallet.ui.registration

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.ViewGroup
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityRegistrationBinding
import com.storiqa.storiqawallet.ui.base.BaseActivity
import com.storiqa.storiqawallet.ui.common.SpacesWatcher
import com.storiqa.storiqawallet.ui.common.onSubmitButtonClicked

class RegistrationActivity : BaseActivity<ActivityRegistrationBinding, RegistrationViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.policyAgreement.movementMethod = LinkMovementMethod.getInstance()
        binding.licenseAgreement.movementMethod = LinkMovementMethod.getInstance()

        binding.etFirstName.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus)
                    viewModel.validateFirstName()
            }
            addTextChangedListener(SpacesWatcher(binding.etFirstName))
        }

        binding.etLastName.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus)
                    viewModel.validateLastName()
            }
            addTextChangedListener(SpacesWatcher(binding.etLastName))
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateEmail()
        }

        binding.etPasswordRepeat.setOnEditorActionListener { textView, actionId, event ->
            onSubmitButtonClicked(textView, actionId, event) { viewModel.onSubmitButtonClicked() }
        }

        binding.scrollView.apply {
            descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
            isFocusable = true
            isFocusableInTouchMode = true
        }
    }


    override fun getLayoutId(): Int = R.layout.activity_registration

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<RegistrationViewModel> = RegistrationViewModel::class.java

}