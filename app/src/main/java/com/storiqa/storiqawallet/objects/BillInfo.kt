package com.storiqa.storiqawallet.objects

import android.support.v4.content.res.ResourcesCompat
import android.view.View
import com.storiqa.storiqawallet.R
import kotlinx.android.synthetic.main.item_bill.view.*
import java.math.BigDecimal

class BillInfo(val bill : Bill) {

    private fun getBillColor() : Int{
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

    private fun getBillImage() : Int {
        return when(bill.tokenType) {
            "STQ" -> {
                return if(isGold()) {
                    R.drawable.stq_gold
                } else {
                    R.drawable.stq_black
                }
            }
            "ETH" -> return R.drawable.cart_ether
            "BTC" -> return R.drawable.bitcoin_cart
            else -> 0
        }
    }

    private fun getBillStatus() : Int {
        return when {
            isRegular() -> R.string.emptyText
            isGold() -> R.string.gold
            isBlack() -> R.string.black
            else -> R.string.emptyText
        }
    }

    private fun getBillTextColor() : Int {
        return when{
            isGold() || isBlack() -> android.R.color.white
            else -> android.R.color.black
        }
    }

    private fun getBillInfoColors() : Int {
        return when{
            isGold() || isBlack() -> R.color.white50oppacity
            else -> R.color.black50oppacity
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

    fun initBillView(root: View) {
        root.tvBillStatus.text = root.context.getString(getBillStatus())
        root.clBill.setBackgroundResource(getBillColor())

        val textColor = ResourcesCompat.getColor(root.context.resources, getBillTextColor(), null)
        root.tvTokenType.setTextColor(textColor)
        root.tvHolderName.setTextColor(textColor)
        root.tvAmount.setTextColor(textColor)
        root.ivBillLogo.setImageResource(getBillImage())

        val additionalInforColor = ResourcesCompat.getColor(root.context.resources, getBillInfoColors(), null)
        root.tvBillStatus.setTextColor(additionalInforColor)
        root.tvAmountInDollars.setTextColor(additionalInforColor)
        root.tvHolderNameLabel.setTextColor(additionalInforColor)
    }
}