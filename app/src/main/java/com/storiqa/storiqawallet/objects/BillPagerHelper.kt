package com.storiqa.storiqawallet.objects

import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import com.storiqa.storiqawallet.adapters.BillsPagerAdapter
import com.storiqa.storiqawallet.constants.Extras
import kotlinx.android.synthetic.main.fragment_wallet_transactions.*
import org.jetbrains.anko.dip

class BillPagerHelper(private val resId : Int, private val fragmentManager: FragmentManager, val onPageChanged : (pageNumber : Int)-> Unit) {

    fun setPager(vpBills : ViewPager, pageIndicator : TabLayout , bills : Array<Bill>, selectedBillId : String) {
        vpBills.adapter = BillsPagerAdapter(resId, fragmentManager, bills)
        vpBills.adapter?.notifyDataSetChanged()
        vpBills.clipToPadding = false
        vpBills.setPadding(vpBills.context.dip(10),0, vpBills.context.dip(10),0)
        pageIndicator.setupWithViewPager(vpBills, true)
        vpBills.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageSelected(pageNumber: Int) {
                onPageChanged(pageNumber)
            }
        })

        val selectedBill = bills.first { it.id == selectedBillId}
        vpBills.currentItem = bills.indexOf(selectedBill)
    }
}