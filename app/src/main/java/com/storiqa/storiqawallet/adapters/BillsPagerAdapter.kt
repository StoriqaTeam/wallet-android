package com.storiqa.storiqawallet.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.screen_main.my_wallet.BillFragment

class BillsPagerAdapter(val resId: Int, fragmentManager: FragmentManager, val bills: Array<Bill>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = BillFragment.getInstance(resId, bills[position])

    override fun getCount(): Int = bills.size

}