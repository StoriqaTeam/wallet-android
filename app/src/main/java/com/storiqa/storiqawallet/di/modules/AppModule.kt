package com.storiqa.storiqawallet.di.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.storiqa.storiqawallet.di.qualifiers.AppContext
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.utils.CryptoSignUtils
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: Application) {

    @Provides
    @PerApplication
    @AppContext
    internal fun provideAppContext(): Context = app

    @Provides
    @PerApplication
    internal fun provideResources(): Resources = app.resources

    @Provides
    @PerApplication
    internal fun provideCryptoSignUtils(): CryptoSignUtils = CryptoSignUtils(app)
}