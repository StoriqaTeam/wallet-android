package com.storiqa.storiqawallet.ui.main.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.storiqa.storiqawallet.data.model.Card
import com.storiqa.storiqawallet.ui.main.account.SingleAccountFragment.Companion.KEY_CARD

class AccountPagerAdapter(fragment: Fragment, private var cards: List<Card>) :
        FragmentStatePagerAdapter(fragment.childFragmentManager) {

    fun updateAccounts(newCards: List<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putParcelable(KEY_CARD, cards[position])
        return SingleAccountFragment().also { it.arguments = bundle }
    }

    override fun getCount(): Int {
        return cards.size
    }
}