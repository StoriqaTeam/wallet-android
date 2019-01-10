package com.storiqa.storiqawallet.ui.main.menu

import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordResetBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment

class MenuFragment : BaseFragment<FragmentPasswordResetBinding, MenuViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_menu

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<MenuViewModel> = MenuViewModel::class.java

}