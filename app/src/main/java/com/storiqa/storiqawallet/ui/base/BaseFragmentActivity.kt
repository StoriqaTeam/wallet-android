package com.storiqa.storiqawallet.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.di.components.ActivityComponent
import com.storiqa.storiqawallet.di.components.DaggerActivityComponent
import com.storiqa.storiqawallet.di.modules.ActivityModule
import com.storiqa.storiqawallet.di.modules.NavigatorModule
import com.storiqa.storiqawallet.network.errors.ErrorPresenterDialog
import com.storiqa.storiqawallet.ui.dialogs.MessageDialog

abstract class BaseFragmentActivity : AppCompatActivity(), IBaseActivity {

    private var loadingDialog: Dialog? = null

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
        } catch (e: NoSuchMethodException) {
            throw NoSuchMethodException("You forgot to add \"fun inject(activity: " +
                    "${this::class.java.simpleName})\" in ActivityComponent")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        hideKeyboard()
        return super.onSupportNavigateUp()
    }

    override fun showErrorDialog(error: ErrorPresenterDialog) {
        val messageDialog = MessageDialog.newInstance(error).apply {
            setPositiveButton(error.positiveButton?.name ?: R.string.button_ok,
                    error.positiveButton?.onClick ?: {})
            val negativeButton = error.negativeButton
            if (negativeButton != null)
                setNegativeButton(negativeButton.name, negativeButton.onClick)

            show(supportFragmentManager, "error dialog")
        }
    }

    override fun showLoadingDialog() {
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

    override fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.cancel()
        }
    }

    override fun setupActionBar(toolbar: Toolbar, title: String, backButtonEnabled: Boolean) {
        setSupportActionBar(toolbar)

        if (backButtonEnabled) {
            val backButton = ContextCompat.getDrawable(this, R.drawable.back)
            val wrappedDrawable = DrawableCompat.wrap(backButton!!)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(this, R.color.button_navigate_up))

            supportActionBar?.setHomeAsUpIndicator(wrappedDrawable)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setTitle(title)
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}