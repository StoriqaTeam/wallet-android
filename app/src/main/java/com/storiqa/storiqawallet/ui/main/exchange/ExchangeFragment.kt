package com.storiqa.storiqawallet.ui.main.exchange

import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordResetBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment

class ExchangeFragment : BaseFragment<FragmentPasswordResetBinding, ExchangeViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_exchange

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<ExchangeViewModel> = ExchangeViewModel::class.java

}