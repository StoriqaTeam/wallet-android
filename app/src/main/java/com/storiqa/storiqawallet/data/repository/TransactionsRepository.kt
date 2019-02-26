package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.AppDatabase
import com.storiqa.storiqawallet.data.db.dao.BlockchainIdDao
import com.storiqa.storiqawallet.data.db.dao.TransactionAccountDao
import com.storiqa.storiqawallet.data.db.dao.TransactionAccountJoinDao
import com.storiqa.storiqawallet.data.db.dao.TransactionDao
import com.storiqa.storiqawallet.data.db.entity.*
import com.storiqa.storiqawallet.data.mapper.TransactionMapper
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.network.requests.CreateTransactionRequest
import com.storiqa.storiqawallet.data.network.responses.TransactionResponse
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.utils.SignUtil
import com.storiqa.storiqawallet.utils.getTimestampLong
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class TransactionsRepository(private val walletApi: WalletApi,
                             private val appDatabase: AppDatabase,
                             private val transactionAccountJoinDao: TransactionAccountJoinDao,
                             private val transactionAccountDao: TransactionAccountDao,
                             private val transactionDao: TransactionDao,
                             private val blockchainIdDao: BlockchainIdDao,
                             private val appDataStorage: IAppDataStorage,
                             private val signUtil: SignUtil) : ITransactionsRepository {

    private val loadingLimit = 30
    private var oldestPendingTransaction = 0L
    private lateinit var accountAddress: String

    override fun getAllTransactionsByAddress(address: String): Flowable<List<Transaction>> {
        accountAddress = address
        return Flowable.combineLatest(loadAllTransactionsByAddress(address),
                loadTransactionAccounts(),
                BiFunction(::mapTransactions))
                .distinct()
    }

    override fun getTransactionsByAddress(address: String, limit: Int): Flowable<List<Transaction>> {
        accountAddress = address
        return Flowable.combineLatest(loadTransactionsByAddress(address, limit),
                loadTransactionAccounts(),
                BiFunction(::mapTransactions))
                .distinct()
    }

    override fun getTransactionById(address: String, id: String): Flowable<Transaction> {
        accountAddress = address
        return Flowable.combineLatest(loadTransactionById(id),
                loadTransactionAccounts(),
                BiFunction(::mapTransaction))
                .distinct()
    }

    override fun refreshTransactions(id: Long, email: String, offset: Int): Observable<List<TransactionResponse>> {
        val token = appDataStorage.token
        val signHeader = signUtil.createSignHeader(email)
        oldestPendingTransaction = appDataStorage.oldestPendingTransactionTime

        return walletApi.getTransactions(id, signHeader.timestamp, signHeader.deviceId,
                signHeader.signature, "Bearer $token", offset, loadingLimit)
                .doOnNext {
                    saveTransactions(it)
                    if (it.size < loadingLimit ||
                            getNewestTransactionTime(it) < oldestPendingTransaction) {
                        val pendingTime = transactionDao.getOldestPendingTransactionTime()
                        appDataStorage.oldestPendingTransactionTime =
                                if (pendingTime != 0L)
                                    pendingTime
                                else
                                    transactionDao.getLastTransactionTime()
                    } else
                        refreshTransactions(id, email, offset + loadingLimit).subscribe({ }, { })
                }
    }

    override fun sendTransaction(email: String, request: CreateTransactionRequest): Observable<Unit> {
        val token = appDataStorage.token
        val signHeader = signUtil.createSignHeader(email)
        return walletApi
                .createTransaction(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(::saveTransaction)
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun sendExchange(email: String, request: CreateTransactionRequest): Observable<Unit> {
        val token = appDataStorage.token
        val signHeader = signUtil.createSignHeader(email)
        return walletApi
                .createTransaction(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(::saveTransaction)
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getNewestTransactionTime(transactions: List<TransactionResponse>): Long { //TODO  poprosit' Seregu sdelat' normal'niy otvet
        return getTimestampLong(transactions.sortedWith(compareBy { it.createdAt }).last().createdAt)
    }

    private fun mapTransactions(transactions: List<TransactionWithAddresses>,
                                accounts: List<TransactionAccountEntity>): List<Transaction> {
        val trMapper = TransactionMapper(accounts)
        return trMapper.map(transactions, accountAddress)
    }

    private fun mapTransaction(transactions: TransactionWithAddresses,
                               accounts: List<TransactionAccountEntity>): Transaction {
        val trMapper = TransactionMapper(accounts)
        return trMapper.map(transactions, accountAddress)
    }

    private fun loadAllTransactionsByAddress(address: String): Flowable<List<TransactionWithAddresses>> {
        return transactionDao
                .loadAllTransactionsByAddress(address)
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    private fun loadTransactionsByAddress(address: String, limit: Int): Flowable<List<TransactionWithAddresses>> {
        return transactionDao
                .loadTransactionsByAddress(address, limit)
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    private fun loadTransactionById(id: String): Flowable<TransactionWithAddresses> {
        return transactionDao
                .loadTransactionById(id)
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    private fun loadTransactionAccounts(): Flowable<List<TransactionAccountEntity>> {
        return transactionAccountDao
                .loadTransactionAccounts()
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    private fun saveTransactions(transactions: List<TransactionResponse>) {

        //blockchainIdDao.deleteAll()
        //transactionAccountJoinDao.deleteAll()
        //transactionAccountDao.deleteAll()
        //transactionDao.deleteAll()

        appDatabase.beginTransaction()
        transactions.forEach(::saveTransaction)
        appDatabase.setTransactionSuccessful()
        appDatabase.endTransaction()
    }

    private fun saveTransaction(transaction: TransactionResponse) {
        val toAccount = transaction.toAccount
        transactionAccountDao.insert(TransactionAccountEntity(toAccount.blockchainAddress, toAccount.accountId, toAccount.ownerName))
        transactionDao.insert(TransactionEntity(transaction.id, transaction.toAccount.blockchainAddress, transaction.fromValue, transaction.fromCurrency,
                transaction.toValue, transaction.toCurrency, transaction.fee, getTimestampLong(transaction.createdAt), getTimestampLong(transaction.updatedAt),
                //if (transaction.id == "8974ddb6-b696-4e9c-a392-34d7dd9f64fa") "pending" else transaction.status,
                transaction.status,
                transaction.fiatValue,
                transaction.fiatCurrency))
        transaction.blockchainTxIds.forEach { blockchainIdDao.insert(BlockchainId(it, transaction.id)) }
        for (transactionAccount in transaction.fromAccount) {
            transactionAccountDao.insert(TransactionAccountEntity(transactionAccount.blockchainAddress, transactionAccount.accountId, transactionAccount.ownerName))
            transactionAccountJoinDao.insert(TransactionAccountJoin(transaction.id, transactionAccount.blockchainAddress))
        }
    }
}