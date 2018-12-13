package com.storiqa.storiqawallet.di.components

import android.content.Context
import android.content.res.Resources
import com.storiqa.storiqawallet.di.modules.AppModule
import com.storiqa.storiqawallet.di.modules.NetworkModule
import com.storiqa.storiqawallet.di.qualifiers.AppContext
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.network.WalletApi
import dagger.Component

@PerApplication
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent : AppComponentProvides {

}

interface AppComponentProvides {

    @AppContext
    fun appContext(): Context

    fun resources(): Resources
    fun walletApi(): WalletApi

}