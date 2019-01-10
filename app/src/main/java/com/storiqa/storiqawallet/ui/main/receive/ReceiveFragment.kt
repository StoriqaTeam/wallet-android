package com.storiqa.storiqawallet.ui.main.receive

import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordResetBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment

class ReceiveFragment : BaseFragment<FragmentPasswordResetBinding, ReceiveViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_receive

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<ReceiveViewModel> = ReceiveViewModel::class.java

}