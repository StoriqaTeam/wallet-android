package com.storiqa.storiqawallet.ui.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentMenuBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity

class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_menu

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<MenuViewModel> = MenuViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        (activity as IBaseActivity).setupActionBar(binding.toolbar)

        subscribeEvents()

        return view
    }

    private fun subscribeEvents() {
        viewModel.errorToast.observe(this, Observer {
            Toast.makeText(requireContext(), R.string.error_unknown_error, Toast.LENGTH_SHORT).show()
        })
    }
}