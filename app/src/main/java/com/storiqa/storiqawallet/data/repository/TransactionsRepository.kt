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
import com.storiqa.storiqawallet.utils.getTimastampLong
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
    private var lastPendingTransaction = Long.MAX_VALUE

    override fun getTransactions(): Flowable<List<Transaction>> {
        return Flowable.combineLatest(getTransactionsWithAddress(),
                getTransactionAccounts(),
                BiFunction(::mapTransactions))
                .distinct()
    }

    override fun getTransactionsByAddress(address: String): Flowable<List<Transaction>> {
        return Flowable.combineLatest(getTransactionsWithAddressByAddress(address),
                getTransactionAccounts(),
                BiFunction(::mapTransactions))
                .distinct()
    }

    private fun mapTransactions(transactions: List<TransactionWithAddresses>,
                                accounts: List<TransactionAccountEntity>): List<Transaction> {
        val trMapper = TransactionMapper(accounts)
        return trMapper.map(transactions)
    }

    private fun getTransactionsWithAddress(): Flowable<List<TransactionWithAddresses>> {
        return transactionDao
                .loadTransactionsWithAddresses()
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    private fun getTransactionsWithAddressByAddress(address: String): Flowable<List<TransactionWithAddresses>> {
        return transactionDao
                .loadTransactionsByAddress(address)
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    private fun getTransactionAccounts(): Flowable<List<TransactionAccountEntity>> {
        return transactionAccountDao
                .loadTransactionAccounts()
                .subscribeOn(Schedulers.io())
                .distinct()
    }

    override fun refreshTransactions(id: Long, email: String, offset: Int): Observable<List<TransactionResponse>> {
        val token = appDataStorage.token
        val signHeader = signUtil.createSignHeader(email)
        lastPendingTransaction = appDataStorage.lastPendingTransactionTime

        return walletApi.getTransactions(id, signHeader.timestamp, signHeader.deviceId,
                signHeader.signature, "Bearer $token", offset, loadingLimit)
                .doOnNext {
                    saveTransactions(it)
                    if (it.size >= loadingLimit || getTimastampLong(it.last().createdAt) > lastPendingTransaction)
                        refreshTransactions(id, email, offset + loadingLimit).subscribe({ }, { })
                    else {
                        val pendingTime = transactionDao.getOldestPendingTransactionTime()
                        appDataStorage.lastPendingTransactionTime = if (pendingTime != 0L) pendingTime else transactionDao.getLastTransactionTime()
                    }
                }
    }

    private fun saveTransactions(transactions: List<TransactionResponse>) {
        appDatabase.beginTransaction()
        for (transaction in transactions) {
            val toAccount = transaction.toAccount
            transactionAccountDao.insert(TransactionAccountEntity(toAccount.blockchainAddress, toAccount.accountId, toAccount.ownerName))
            transactionDao.insert(TransactionEntity(transaction.id, transaction.toAccount.blockchainAddress, transaction.fromValue, transaction.fromCurrency,
                    transaction.toValue, transaction.toCurrency, transaction.fee, getTimastampLong(transaction.createdAt), getTimastampLong(transaction.updatedAt), transaction.status, transaction.fiatValue,
                    transaction.fiatCurrency))
            transaction.blockchainTxIds.forEach { blockchainIdDao.insert(BlockchainId(it, transaction.id)) }
            for (transactionAccount in transaction.fromAccount) {
                transactionAccountDao.insert(TransactionAccountEntity(transactionAccount.blockchainAddress, transactionAccount.accountId, transactionAccount.ownerName))
                transactionAccountJoinDao.insert(TransactionAccountJoin(transaction.id, transactionAccount.blockchainAddress))
            }
        }
        appDatabase.setTransactionSuccessful()
        appDatabase.endTransaction()

        val q = transactionDao.getLastTransactionTime()
        print("qq")
    }
}