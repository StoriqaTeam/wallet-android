package com.storiqa.storiqawallet.objects

import java.io.Serializable

data class Bill(val tokenType : String, val amountInStq : String, val amountInDollars : String, val holderName : String, val isPremium : Boolean) : Serializable
//TODO change for parcel