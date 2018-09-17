package com.storiqa.storiqawallet.objects

import com.storiqa.storiqawallet.R
import java.math.BigDecimal

class BillInfo(val bill : Bill) {

    fun getBillColor() : Int{
        return if(!bill.tokenType.equals("STQ")) {
            R.drawable.bill_gray
        } else {
            when {
                isRegular() -> R.drawable.bill_gray
                isGold() -> R.drawable.bill_gold
                else -> R.drawable.bill_black
            }
        }
    }

    fun getBillImage() : Int {
        return when(bill.tokenType) {
            "STQ" -> return R.drawable.eth //TODO add images
            "ETH" -> return R.drawable.eth
            "BTC" -> return R.drawable.eth
            else -> 0
        }
    }

    fun getBillStatus() : Int {
        return when {
            isRegular() -> R.string.emptyText
            isGold() -> R.string.gold
            isBlack() -> R.string.black
            else -> R.string.emptyText
        }
    }

    fun getBillTextColor() : Int {
        return when{
            isGold() || isBlack() -> android.R.color.white
            else -> android.R.color.black
        }
    }

    private fun isRegular() : Boolean {
        val bdAmount = BigDecimal(bill.amount)
        return bdAmount < BigDecimal(1000000)
    }

    private fun isGold() : Boolean {
        val bdAmount = BigDecimal(bill.amount)
        return isStq() && bdAmount >= BigDecimal(1000000) && bdAmount < BigDecimal(5000000)
    }

    private fun isBlack() : Boolean {
        val bdAmount = BigDecimal(bill.amount)
        return isStq() && bdAmount > BigDecimal(5000000)
    }

    private fun isStq() : Boolean {
        return bill.tokenType == "STQ"
    }
}