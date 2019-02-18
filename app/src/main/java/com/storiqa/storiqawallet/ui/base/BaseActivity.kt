package com.storiqa.storiqawallet.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.di.components.ActivityComponent
import com.storiqa.storiqawallet.di.components.DaggerActivityComponent
import com.storiqa.storiqawallet.di.modules.ActivityModule
import com.storiqa.storiqawallet.di.modules.NavigatorModule
import com.storiqa.storiqawallet.ui.base.navigator.IBaseNavigator
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel<N>, N : IBaseNavigator> : AppCompatActivity(), IBaseActivity {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: N

    protected lateinit var binding: B

    protected lateinit var viewModel: VM

    private var loadingDialog: Dialog? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun getViewModelClass(): Class<VM>

    override val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .navigatorModule(NavigatorModule(this))
                .appComponent(App.appComponent)
                .build()
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            ActivityComponent::class.java.getDeclaredMethod("inject", this::class.java)
                    .invoke(activityComponent, this)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        } catch (e: NoSuchMethodException) {
            throw NoSuchMethodException("You forgot to add \"fun inject(activity: " +
                    "${this::class.java.simpleName})\" in ActivityComponent")
        }

        viewModel.setNavigator(navigator)

        performDataBinding()
        subscribeEvents()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onViewStarted()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onViewStopped()
    }

    override fun showLoadingDialog() {
        if (loadingDialog != null && loadingDialog?.isShowing!!)
            return

        loadingDialog = Dialog(this).apply {
            show()
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dialog_progress)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.cancel()
        }
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun setupActionBar(toolbar: Toolbar, title: String?, backButtonEnabled: Boolean) {
        setSupportActionBar(toolbar)

        if (backButtonEnabled) {
            val backButton = ContextCompat.getDrawable(this, R.drawable.back)
            val wrappedDrawable = DrawableCompat.wrap(backButton!!)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(this, R.color.button_navigate_up))

            supportActionBar?.setHomeAsUpIndicator(wrappedDrawable)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        if (title != null)
            setTitle(title)
    }

    open fun subscribeEvents() {
        viewModel.showLoadingDialog.observe(this,
                Observer { if (it != null && it) showLoadingDialog() else hideLoadingDialog() })

        viewModel.hideKeyboard.observe(this, Observer { hideKeyboard() })
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setVariable(getBindingVariable(), viewModel)
        binding.executePendingBindings()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}