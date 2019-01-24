package com.storiqa.storiqawallet.data.mapper

import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.CurrencyFormatter
import com.storiqa.storiqawallet.common.ICurrencyConverter
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.model.Card

class AccountMapper(private val currencyConverter: ICurrencyConverter) : IAccountMapper {

    private val currencyFormatter = CurrencyFormatter()

    override fun map(account: AccountEntity): Card {
        val currency = account.currency
        val balanceDecimal = currencyFormatter.getFormattedDecimal(account.balance, currency)
        val balanceFormatted = currencyFormatter.getFormattedString(balanceDecimal, currency)
        val balanceFiatFormatted = currencyFormatter.getFormattedString(
                currencyConverter.convertToFiat(balanceDecimal, currency), currency)

        return Card(account.id,
                account.userId,
                account.balance,
                balanceFormatted,
                balanceFiatFormatted,
                account.currency,
                getCurrencyIcon(currency),
                account.accountAddress,
                account.name)
    }

    private fun getCurrencyIcon(currencyISO: String): Int {
        return when (currencyISO) {
            "btc" -> R.drawable.btc_small_logo
            "eth" -> R.drawable.eth_small_logo
            "stq" -> R.drawable.stq_small_logo
            else -> throw Exception("Not found icon for $currencyISO")
        }
    }

}