package com.storiqa.storiqawallet.di.modules

import com.storiqa.storiqawallet.BuildConfig
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.providers.ILoginNetworkProvider
import com.storiqa.storiqawallet.network.providers.LoginNetworkProvider
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

    @Provides
    @PerApplication
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @PerApplication
    internal fun provideWalletApi(okHttpClient: OkHttpClient): WalletApi {
        val httpClientBuilder = okHttpClient.newBuilder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(WalletApi::class.java)
    }

    @Provides
    @PerApplication
    internal fun provideLoginNetworkProvider(walletApi: WalletApi): ILoginNetworkProvider {
        return LoginNetworkProvider(walletApi)
    }

}