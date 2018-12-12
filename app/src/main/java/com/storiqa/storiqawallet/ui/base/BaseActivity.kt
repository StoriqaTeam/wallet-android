package com.storiqa.storiqawallet.ui.base

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.di.components.ActivityComponent
import com.storiqa.storiqawallet.di.components.DaggerActivityComponent
import com.storiqa.storiqawallet.di.modules.ActivityModule
import com.storiqa.storiqawallet.di.modules.NavigatorModule
import com.storiqa.storiqawallet.network.errors.ErrorPresenterDialog
import com.storiqa.storiqawallet.ui.dialogs.ARGUMENT_ICON
import com.storiqa.storiqawallet.ui.dialogs.ARGUMENT_MESSAGE
import com.storiqa.storiqawallet.ui.dialogs.ARGUMENT_TITLE
import com.storiqa.storiqawallet.ui.dialogs.MessageDialog
import javax.inject.Inject


abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel<*>> : AppCompatActivity() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var binding: B

    protected lateinit var viewModel: VM

    private var loadingDialog: Dialog? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun getViewModelClass(): Class<VM>

    internal val activityComponent: ActivityComponent by lazy {
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

        performDataBinding()
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.showLoadingDialog.observe(this,
                Observer { if (it!!) showLoadingDialog() else hideLoadingDialog() })

        viewModel.hideKeyboard.observe(this, Observer { hideKeyboard() })

        viewModel.showErrorDialog.observe(this, Observer { showErrorDialog(it!!) })
    }

    private fun showErrorDialog(error: ErrorPresenterDialog) {
        val bundle = Bundle()
        bundle.putInt(ARGUMENT_TITLE, error.title)
        bundle.putInt(ARGUMENT_MESSAGE, error.description)
        bundle.putInt(ARGUMENT_ICON, error.icon)
        val messageDialog = MessageDialog().apply {
            arguments = bundle
            setPositiveButton(error.positiveButton.name, error.positiveButton.onClick)
            val negativeButton = error.negativeButton
            if (negativeButton != null)
                setNegativeButton(negativeButton.name, negativeButton.onClick)

            show(supportFragmentManager, "error dialog")
        }
    }

    private fun showLoadingDialog() {
        if (loadingDialog != null && loadingDialog?.isShowing!!)
            return

        loadingDialog = Dialog(this).apply {
            show()
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.progress_dialog)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    private fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.cancel()
        }
    }

    protected fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setVariable(getBindingVariable(), viewModel)
        binding.executePendingBindings()
    }
}