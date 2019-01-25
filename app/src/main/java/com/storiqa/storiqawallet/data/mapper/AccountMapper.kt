package com.storiqa.storiqawallet.data.mapper

import com.storiqa.storiqawallet.common.CurrencyFormatter
import com.storiqa.storiqawallet.common.ICurrencyConverter
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.model.Card

class AccountMapper(private val currencyConverter: ICurrencyConverter) : IAccountMapper {

    private val currencyFormatter = CurrencyFormatter()

    override fun map(account: AccountEntity): Card {
        val currency = account.currency
        val balanceDecimal = currencyFormatter.getFormattedDecimal(account.balance, currency)
        val balanceFormatted = currencyFormatter.getBalanceWithoutSymbol(balanceDecimal, currency)
        val balanceFiatFormatted = currencyFormatter.getBalanceWithFiatSymbol(
                currencyConverter.convertToFiat(balanceDecimal, currency), currency)

        return Card(account.id,
                account.userId,
                account.balance,
                balanceFormatted,
                balanceFiatFormatted,
                currency.getCardBackground(),
                account.currency.currencyISO,
                currency.getCurrencyIcon(),
                account.accountAddress,
                account.name)
    }

}