package com.storiqa.storiqawallet.adapters

import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.screen_main.my_wallet.BillFragment

class BillsPagerAdapter(val resId: Int, fragmentManager: androidx.fragment.app.FragmentManager, val bills: Array<Bill>) : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment = BillFragment.getInstance(resId, bills[position])

    override fun getCount(): Int = bills.size

}