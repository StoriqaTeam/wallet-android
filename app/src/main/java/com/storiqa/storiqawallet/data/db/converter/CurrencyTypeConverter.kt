package com.storiqa.storiqawallet.data.db.converter

import androidx.room.TypeConverter
import com.storiqa.storiqawallet.data.model.Currency

class CurrencyTypeConverter {

    @TypeConverter
    fun currencyToString(currency: Currency?): String? {
        return currency?.currencyISO
    }

    @TypeConverter
    fun stringToCurrency(currencyISO: String?): Currency? {
        return if (currencyISO.isNullOrEmpty())
            null
        else
            Currency.valueOf(currencyISO)
    }
}