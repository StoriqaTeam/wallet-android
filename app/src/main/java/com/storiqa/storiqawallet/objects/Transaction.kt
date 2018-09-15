package com.storiqa.storiqawallet.objects

import com.storiqa.storiqawallet.enums.TransactionType

data class Transaction(val tokenType : String,
                       val transactionType : TransactionType,
                       val isPending : Boolean,
                       val amountInToken : String,
                       val amountInDollars : String,
                       val transactionSenderWallet : String,
                       val transactionReceiverName : String)