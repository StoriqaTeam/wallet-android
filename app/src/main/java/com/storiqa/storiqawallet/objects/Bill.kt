package com.storiqa.storiqawallet.objects

import java.math.BigDecimal

data class Bill(val tokenType : String, val amountInStq : String, val amountInDollars : String, val holderName : String, val isPremium : Boolean)