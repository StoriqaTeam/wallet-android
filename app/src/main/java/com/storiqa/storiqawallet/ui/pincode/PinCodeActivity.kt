package com.storiqa.storiqawallet.ui.pincode

import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityPinCodeBinding
import com.storiqa.storiqawallet.ui.base.BaseActivity

class PinCodeActivity : BaseActivity<ActivityPinCodeBinding, PinCodeViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_pin_code

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<PinCodeViewModel> = PinCodeViewModel::class.java
}