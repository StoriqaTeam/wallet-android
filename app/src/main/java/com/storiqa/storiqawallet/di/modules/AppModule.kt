package com.storiqa.storiqawallet.di.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.storiqa.storiqawallet.di.qualifiers.AppContext
import com.storiqa.storiqawallet.di.scopes.PerApplication
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

}