package com.storiqa.storiqawallet.data.mapper

import com.storiqa.storiqawallet.common.resolveCurrencyIcon
import com.storiqa.storiqawallet.common.resolveCurrencySymbol
import com.storiqa.storiqawallet.common.resolveExp
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.model.Card
import java.math.BigDecimal
import java.math.RoundingMode

class AccountMapper(private val rates: List<RateEntity>,
                    private val currencyFiat: String = "USD") : IAccountMapper {

    override fun map(account: AccountEntity): Card {
        val currency = account.currency
        var coefficient = 0.0
        for (rate in rates) {
            if (rate.currencyCrypto.equals(currency, true) &&
                    rate.currencyFiat.equals(currencyFiat, true)) {
                coefficient = rate.price
                break
            }
        }

        val exp = resolveExp(currency)

        val balanceDecimal = BigDecimal(account.balance).movePointLeft(exp)

        var balanceFiat = balanceDecimal.multiply(BigDecimal(coefficient)).stripTrailingZeros()
        if (balanceFiat.compareTo(BigDecimal.ZERO) == 0)
            balanceFiat = BigDecimal.ZERO

        var balanceCrypto = balanceDecimal.setScale(exp, RoundingMode.DOWN).stripTrailingZeros()
        if (balanceCrypto.compareTo(BigDecimal.ZERO) == 0)
            balanceCrypto = BigDecimal.ZERO

        val balanceFormatted = balanceCrypto.toPlainString() + ' ' + account.currency.toUpperCase()
        val balanceFiatStr = resolveCurrencySymbol(currencyFiat) + ' ' +
                balanceFiat.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()

        return Card(account.id,
                account.userId,
                account.balance,
                balanceFormatted,
                balanceFiatStr,
                account.currency,
                resolveCurrencyIcon(currency),
                account.accountAddress,
                account.name)
    }

}