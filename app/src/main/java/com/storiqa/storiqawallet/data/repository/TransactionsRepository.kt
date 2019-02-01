package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.db.AppDatabase
import com.storiqa.storiqawallet.data.db.dao.BlockchainIdDao
import com.storiqa.storiqawallet.data.db.dao.TransactionAccountDao
import com.storiqa.storiqawallet.data.db.dao.TransactionAccountJoinDao
import com.storiqa.storiqawallet.data.db.dao.TransactionDao
import com.storiqa.storiqawallet.data.db.entity.*
import com.storiqa.storiqawallet.data.mapper.TransactionMapper
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.responses.TransactionResponse
import com.storiqa.storiqawallet.utils.SignUtil
import com.storiqa.storiqawallet.utils.getTimestampLong
import io.reactivex.Flowable
import io.reactivex.Observable
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

    /*override fun getAllTransactions(): Flowable<List<Transaction>> {
        return Flowable.combineLatest(loadAllTransactions(),
                loadTransactionAccounts(),
                BiFunction(::mapTransactions))
                .distinct()
    }

    override fun getTransactions(limit: Int): Flowable<List<Transaction>> {
        return Flowable.combineLatest(loadTransactionsWithLimit(limit),
                loadTransactionAccounts(),
                BiFunction(::mapTransactions))
                .distinct()
    }*/

    override fun getTransactionsByAddress(address: String, limit: Int): Flowable<List<Transaction>> {
        accountAddress = address
        return Flowable.combineLatest(loadTransactionsByAddress(address, limit),
                loadTransactionAccounts(),
                BiFunction(::mapTransactions))
                .distinct()
    }

    private fun mapTransactions(transactions: List<TransactionWithAddresses>,
                                accounts: List<TransactionAccountEntity>): List<Transaction> {
        val trMapper = TransactionMapper(accounts)
        return trMapper.map(transactions, accountAddress)
    }

    private fun loadAllTransactions(): Flowable<List<TransactionWithAddresses>> {
        return transactionDao
                .loadAllTransactions()
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    private fun loadTransactionsWithLimit(limit: Int): Flowable<List<TransactionWithAddresses>> {
        return transactionDao
                .loadTransactionsWithLimit(limit)
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    private fun loadTransactionsByAddress(address: String, limit: Int): Flowable<List<TransactionWithAddresses>> {
        return transactionDao
                .loadTransactionsByAddress(address, limit)
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    private fun loadTransactionAccounts(): Flowable<List<TransactionAccountEntity>> {
        return transactionAccountDao
                .loadTransactionAccounts()
                .subscribeOn(Schedulers.io())
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

    private fun getNewestTransactionTime(transactions: List<TransactionResponse>): Long { //TODO  poprosit' Seregu sdelat' normal'niy otvet
        return getTimestampLong(transactions.sortedWith(compareBy { it.createdAt }).last().createdAt)
    }

    private fun saveTransactions(transactions: List<TransactionResponse>) {

        //blockchainIdDao.deleteAll()
        //transactionAccountJoinDao.deleteAll()
        //transactionAccountDao.deleteAll()
        //transactionDao.deleteAll()

        appDatabase.beginTransaction()
        for (transaction in transactions) {
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
        appDatabase.setTransactionSuccessful()
        appDatabase.endTransaction()
    }
}