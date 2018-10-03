package com.storiqa.storiqawallet.objects

import java.io.Serializable

data class Bill(val id : String, val tokenType : String, val amount : String, val amountInDollars : String, val holderName : String) : Serializable
//TODO change for parcel