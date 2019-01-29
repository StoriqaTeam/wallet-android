package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.db.dao.BlockchainIdDao
import com.storiqa.storiqawallet.data.db.dao.TransactionAccountDao
import com.storiqa.storiqawallet.data.db.dao.TransactionAccountJoinDao
import com.storiqa.storiqawallet.data.db.dao.TransactionDao
import com.storiqa.storiqawallet.data.db.entity.*
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.responses.TransactionResponse
import com.storiqa.storiqawallet.utils.SignUtil
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class TransactionsRepository(private val walletApi: WalletApi,
                             private val transactionAccountJoinDao: TransactionAccountJoinDao,
                             private val transactionAccountDao: TransactionAccountDao,
                             private val transactionDao: TransactionDao,
                             private val blockchainIdDao: BlockchainIdDao,
                             private val appDataStorage: IAppDataStorage,
                             private val signUtil: SignUtil) : ITransactionsRepository {

    override fun getTransactions(): Flowable<List<TransactionWithAddresses>> {
        return transactionDao.loadTransactionsWithAddresses().subscribeOn(Schedulers.io()).distinct()
    }

    override fun getTransactionAccounts(): Flowable<List<TransactionAccountEntity>> {
        return transactionAccountDao.loadTransactionAccounts().subscribeOn(Schedulers.io()).distinct()
    }

    override fun refreshTransactions(id: Long, email: String): Observable<List<TransactionResponse>> {
        val token = appDataStorage.token
        val signHeader = signUtil.createSignHeader(email)

        return walletApi.getTransactions(id, signHeader.timestamp, signHeader.deviceId,
                signHeader.signature, "Bearer $token", 0, 1000)
                .doOnNext { saveTransactions(it) }
    }

    private fun saveTransactions(transactions: List<TransactionResponse>) {
        for (transaction in transactions) {
            val toAccount = transaction.toAccount
            transactionAccountDao.insert(TransactionAccountEntity(toAccount.blockchainAddress, toAccount.accountId, toAccount.ownerName))
            transactionDao.insert(TransactionEntity(transaction.id, transaction.toAccount.blockchainAddress, transaction.fromValue, transaction.fromCurrency,
                    transaction.toValue, transaction.toCurrency, transaction.fee, transaction.createdAt, transaction.updatedAt, transaction.status, transaction.fiatValue,
                    transaction.fiatCurrency))
            transaction.blockchainTxIds.forEach { blockchainIdDao.insert(BlockchainId(it, transaction.id)) }
            for (transactionAccount in transaction.fromAccount) {
                transactionAccountDao.insert(TransactionAccountEntity(transactionAccount.blockchainAddress, transactionAccount.accountId, transactionAccount.ownerName))
                transactionAccountJoinDao.insert(TransactionAccountJoin(transaction.id, transactionAccount.blockchainAddress))
            }
        }
    }
}