package com.storiqa.storiqawallet.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.storiqa.storiqawallet.di.components.DaggerFragmentComponent
import com.storiqa.storiqawallet.di.components.FragmentComponent
import com.storiqa.storiqawallet.di.modules.FragmentModule
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel<*>> : Fragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var binding: B

    protected lateinit var viewModel: VM

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun getViewModelClass(): Class<VM>

    internal val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .activityComponent((activity as IBaseActivity).activityComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            FragmentComponent::class.java.getDeclaredMethod("inject", this::class.java)
                    .invoke(fragmentComponent, this)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        } catch (e: NoSuchMethodException) {
            throw NoSuchMethodException("You forgot to add \"fun inject(fragment: " +
                    "${this::class.java.simpleName})\" in FragmentComponent")
        }

        subscribeEvents()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        performDataBinding(inflater, container)
        return binding.root
    }

    private fun subscribeEvents() {
        viewModel.showLoadingDialog.observe(this,
                Observer {
                    if (it != null && it) getBaseActivity()?.showLoadingDialog()
                    else getBaseActivity()?.hideLoadingDialog()
                })

        viewModel.hideKeyboard.observe(this, Observer { getBaseActivity()?.hideKeyboard() })

        viewModel.showMessageDialog.observe(this,
                Observer { getBaseActivity()?.showErrorDialog(it!!) })
    }

    private fun performDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.setVariable(getBindingVariable(), viewModel)
        binding.executePendingBindings()
    }

    private fun getBaseActivity(): IBaseActivity? {
        return activity as IBaseActivity
    }
}