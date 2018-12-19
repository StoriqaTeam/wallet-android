package com.storiqa.storiqawallet.ui.registration

import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityRegistrationBinding
import com.storiqa.storiqawallet.ui.base.BaseActivity

class RegistrationActivity : BaseActivity<ActivityRegistrationBinding, RegistrationViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_registration

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<RegistrationViewModel> = RegistrationViewModel::class.java


}