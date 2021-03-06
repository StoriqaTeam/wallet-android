package com.storiqa.storiqawallet.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityMainBinding
import com.storiqa.storiqawallet.ui.base.BaseActivity
import com.storiqa.storiqawallet.ui.main.MainViewState.*
import com.storiqa.storiqawallet.ui.main.wallet.WalletFragment


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel, IMainNavigator>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_main

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    override fun onStart() {
        super.onStart()

        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()

        viewModel.onStop()
    }

    private fun initView() {
        val bottomNavigation = binding.bottomNavigation
        val navigationAdapter = AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu)
        navigationAdapter.setupWithBottomNavigation(bottomNavigation)
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavigation.setOnTabSelectedListener(viewModel::onMenuItemSelected)
        bottomNavigation.defaultBackgroundColor = resources.getColor(R.color.bottom_navigation_background)
        bottomNavigation.accentColor = resources.getColor(R.color.bottom_navigation_icon_selected)
        bottomNavigation.inactiveColor = resources.getColor(R.color.bottom_navigation_icon_unselected)
        bottomNavigation.setTitleTypeface(ResourcesCompat.getFont(this, R.font.montserrat_medium))
    }

    override fun subscribeEvents() {
        super.subscribeEvents()

        viewModel.viewState.observe(this, Observer(::onViewStateChanged))
    }

    private fun onViewStateChanged(state: MainViewState) {
        when (state) {
            CONTENT -> {
                showWalletFragment()
                binding.container.visibility = View.VISIBLE
                hideLoadingDialog()
            }
            LOADING -> {
                binding.container.visibility = View.INVISIBLE
                showLoadingDialog()
            }
            STUB -> {
                binding.container.visibility = View.INVISIBLE

            }
        }
    }

    private fun showWalletFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, WalletFragment(), "wallet")
                .commitNow()
    }
}