package com.storiqa.storiqawallet.objects

import com.google.android.material.tabs.TabLayout
import com.storiqa.storiqawallet.adapters.BillsPagerAdapter

class BillPagerHelper(private val resId: Int, private val fragmentManager: androidx.fragment.app.FragmentManager, val onPageChanged: (pageNumber: Int) -> Unit) {

    fun setPager(vpBills: androidx.viewpager.widget.ViewPager, pageIndicator: TabLayout, bills: Array<Bill>, selectedBillId: String) {
        vpBills.adapter = BillsPagerAdapter(resId, fragmentManager, bills)
        vpBills.adapter?.notifyDataSetChanged()
        vpBills.clipToPadding = false
        //vpBills.setPadding(vpBills.context.dip(10), 0, vpBills.context.dip(10), 0)
        pageIndicator.setupWithViewPager(vpBills, true)
        vpBills.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageSelected(pageNumber: Int) {
                onPageChanged(pageNumber)
            }
        })

        val selectedBill = bills.first { it.id == selectedBillId }
        vpBills.currentItem = bills.indexOf(selectedBill)
    }
}