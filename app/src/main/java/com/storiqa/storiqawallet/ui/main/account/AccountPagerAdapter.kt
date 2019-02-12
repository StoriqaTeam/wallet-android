package com.storiqa.storiqawallet.ui.main.account

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.model.AccountCardSize

class AccountPagerAdapter(
        fragment: Fragment,
        private var cards: List<Account>,
        private val size: AccountCardSize) :
        FragmentStatePagerAdapter(fragment.childFragmentManager) {

    fun updateAccounts(newCards: List<Account>) {
        cards = newCards
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return when (size) {
            AccountCardSize.SMALL -> LargeAccountFragment.newInstance(cards[position])
            AccountCardSize.MEDIUM -> MediumAccountFragment.newInstance(cards[position])
            AccountCardSize.LARGE -> LargeAccountFragment.newInstance(cards[position])
        }
    }

    override fun getCount(): Int {
        return cards.size
    }

    override fun getItemPosition(obj: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}