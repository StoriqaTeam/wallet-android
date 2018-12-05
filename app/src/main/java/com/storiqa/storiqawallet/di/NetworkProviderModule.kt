package com.storiqa.storiqawallet.di

import com.storiqa.storiqawallet.network.providers.ILoginNetworkProvider
import com.storiqa.storiqawallet.network.providers.LoginNetworkProvider
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val networkProviderModule = Kodein.Module("networkProviderModule") {

    import(apiModule)

    bind<ILoginNetworkProvider>() with singleton { LoginNetworkProvider(instance()) }

}