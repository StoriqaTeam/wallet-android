package com.storiqa.storiqawallet.ui.dialogs

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.android.databinding.library.baseAdapters.BR
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.databinding.DialogMessageBinding
import com.storiqa.storiqawallet.di.components.DaggerFragmentComponent
import com.storiqa.storiqawallet.di.components.FragmentComponent
import com.storiqa.storiqawallet.di.modules.FragmentModule
import com.storiqa.storiqawallet.network.errors.ErrorPresenterDialog
import com.storiqa.storiqawallet.ui.base.BaseActivity
import javax.inject.Inject


const val ARGUMENT_TITLE = "argument_title"
const val ARGUMENT_MESSAGE = "argument_message"
const val ARGUMENT_ICON = "argument_icon"
const val ARGUMENT_POSITIVE_BUTTON = "positive_button"
const val ARGUMENT_NEGATIVE_BUTTON = "negative_button"

class MessageDialog : DialogFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MessageViewModel

    private lateinit var onPositiveClick: () -> Unit
    private var onNegativeClick: (() -> Unit)? = null

    internal val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .activityComponent((activity as BaseActivity<*, *>).activityComponent)
                .build()
    }

    companion object {

        @JvmStatic
        fun newInstance(error: ErrorPresenterDialog) = MessageDialog().apply {
            arguments = Bundle().apply {
                putInt(ARGUMENT_TITLE, error.title)
                putInt(ARGUMENT_MESSAGE, error.description)
                putInt(ARGUMENT_ICON, error.icon)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            FragmentComponent::class.java.getDeclaredMethod("inject", this::class.java)
                    .invoke(fragmentComponent, this)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(MessageViewModel::class.java)
        } catch (e: NoSuchMethodException) {
            throw NoSuchMethodException("You forgot to add \"fun inject(activity: " +
                    "${this::class.java.simpleName})\" in ActivityComponent")
        }

        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.positiveButtonClicked.observe(this, Observer {
            dismiss()
            onPositiveClick()
        })

        viewModel.negativeButtonClicked.observe(this, Observer {
            dismiss()
            onNegativeClick?.invoke()
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        val args = arguments
        if (args != null) {
            val btnNegativeText = if (onNegativeClick == null) null else
                App.res.getString(args.getInt(ARGUMENT_NEGATIVE_BUTTON))

            viewModel.initData(
                    App.res.getString(args.getInt(ARGUMENT_TITLE)),
                    App.res.getString(args.getInt(ARGUMENT_MESSAGE)),
                    App.res.getDrawable(args.getInt(ARGUMENT_ICON)),
                    App.res.getString(args.getInt(ARGUMENT_POSITIVE_BUTTON)),
                    btnNegativeText)
        }

        val binding = DialogMessageBinding.inflate(activity!!.layoutInflater)
        builder.setView(binding.root)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()

        return builder.create()
    }

    fun setPositiveButton(name: Int, onClick: () -> Unit) {
        arguments?.putInt(ARGUMENT_POSITIVE_BUTTON, name)
        onPositiveClick = onClick
    }

    fun setNegativeButton(name: Int, onClick: () -> Unit) {
        arguments?.putInt(ARGUMENT_NEGATIVE_BUTTON, name)
        onNegativeClick = onClick

    }
}