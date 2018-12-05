package com.storiqa.storiqawallet.ui.base

import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.addOnPropertyChanged


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity() {

    private var loadingDialog: Dialog? = null
    private var viewDataBinding: T? = null
    private var viewModel: V? = null

    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        initObservers()
    }

    private fun initObservers() {
        viewModel?.isLoading?.addOnPropertyChanged {
            if (it.get())
                showLoadingDialog(this)
            else
                hideLoadingDialog()
        }

        viewModel?.hideKeyboard?.observe(this, Observer { hideKeyboard() })
    }

    private fun showLoadingDialog(context: Context) {
        if (loadingDialog != null && loadingDialog?.isShowing!!)
            return

        loadingDialog = Dialog(context)
        loadingDialog!!.show()
        loadingDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog!!.setContentView(R.layout.progress_dialog)
        loadingDialog!!.setCancelable(false)
        loadingDialog!!.setCanceledOnTouchOutside(false)
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

    protected fun openActivity(clsActivity: Class<out Activity>, isFinish: Boolean) {
        val intent = Intent(this, clsActivity)
        startActivity(intent)

        if (isFinish)
            finish()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = if (viewModel == null) getViewModel() else viewModel
        viewDataBinding?.setVariable(getBindingVariable(), viewModel)
        viewDataBinding?.executePendingBindings()
    }
}