package com.storiqa.storiqawallet.di

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.storiqa.storiqawallet.ui.login.LoginActivity
import com.storiqa.storiqawallet.ui.login.LoginViewModel
import org.kodein.di.Kodein
import org.kodein.di.android.support.AndroidLifecycleScope
import org.kodein.di.generic.*


val viewModelModule = Kodein.Module("viewModel") {

    import(networkProviderModule)

    bind<ViewModelFactory>() with singleton {
        ViewModelFactory(kodein)
    }

    bind<ViewModel>("LoginViewModel") with provider {
        LoginViewModel(instance<LoginActivity>(), instance())
    }

    bind<LoginViewModel>() with scoped(AndroidLifecycleScope<Activity>()).singleton {
        getViewModel(instance<LoginActivity>(), instance(), LoginViewModel::class.java)
    }

}

private fun <T : ViewModel> getViewModel(
        activity: FragmentActivity,
        factory: ViewModelFactory,
        modelClass: Class<T>) = ViewModelProviders.of(activity, factory).get(modelClass)

private class ViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider: () -> ViewModel by kodein.provider(modelClass.simpleName)
        return provider() as T
    }
}