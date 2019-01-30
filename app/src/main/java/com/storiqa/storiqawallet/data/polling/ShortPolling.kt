package com.storiqa.storiqawallet.data.polling

import com.storiqa.storiqawallet.data.model.Currency
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.network.responses.AccountResponse
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class ShortPolling(private val accountsRepository: IAccountsRepository,
                   private val ratesRepository: IRatesRepository) : IShortPolling {

    private var shortPolling: Disposable? = null
    private val shortPollingPeriod = 30L

    override fun start(id: Long, email: String) {
        shortPolling?.dispose()

        //TODO check token expired

        shortPolling = Observable.interval(0, shortPollingPeriod, TimeUnit.SECONDS)
                .flatMap {
                    Observable.zip(accountsRepository.updateAccounts(id, email),
                            ratesRepository.updateRates(),
                            BiFunction<ArrayList<AccountResponse>, HashMap<Currency, HashMap<Currency, Double>>, Unit?> { _, _ -> null })
                }
                .observeOn(Schedulers.io())
                .subscribe({}, {})
    }

    override fun stop() {
        shortPolling?.dispose()
    }
}