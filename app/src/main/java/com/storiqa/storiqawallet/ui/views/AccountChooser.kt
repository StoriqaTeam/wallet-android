package com.storiqa.storiqawallet.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.ui.main.account.AccountCardSize
import com.storiqa.storiqawallet.ui.main.account.AccountPagerAdapter
import com.storiqa.storiqawallet.utils.convertDpToPx
import kotlinx.android.synthetic.main.view_account_chooser.view.*


class AccountChooser
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    private var onPageSelectedListener: ((Int, String) -> Unit)? = null
    var accounts: List<Account> = ArrayList()
        set(value) {
            field = value
            updateAccounts()
        }
    private var accountsAdapter: AccountPagerAdapter? = null
    private val size: AccountCardSize
    private val isIndicatorEnable: Boolean
    private var initialPosition = -1

    init {
        LayoutInflater.from(context).inflate(R.layout.view_account_chooser, this, true)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.AccountChooser)
        val cardSize = attributes.getInt(R.styleable.AccountChooser_cardSize, 1)
        isIndicatorEnable = attributes.getBoolean(R.styleable.AccountChooser_indicatorEnable, false)
        attributes.recycle()

        pageIndicator.visibility = if (isIndicatorEnable) View.VISIBLE else View.GONE

        size = when (cardSize) {
            0 -> AccountCardSize.SMALL
            1 -> AccountCardSize.MEDIUM
            2 -> AccountCardSize.LARGE
            else -> throw Exception("not valid card size")
        }

        accountsPager.layoutParams.height = when (cardSize) {
            0 -> resources.getDimension(com.storiqa.storiqawallet.R.dimen.smallAccountHeight).toInt()
            1 -> resources.getDimension(com.storiqa.storiqawallet.R.dimen.mediumAccountHeight).toInt()
            2 -> resources.getDimension(com.storiqa.storiqawallet.R.dimen.largeAccountHeight).toInt()
            else -> throw Exception("not valid card size")
        }

        if (isIndicatorEnable)
            pageIndicator.setViewPager(accountsPager)
    }


    fun init(fragment: Fragment, initialPosition: Int = 0) {
        accountsAdapter = AccountPagerAdapter(fragment, accounts, size)
        accountsPager.apply {
            adapter = accountsAdapter
            setPadding(convertDpToPx(30f).toInt(), 0, convertDpToPx(30f).toInt(), 0)
            clipToPadding = false
            pageMargin = convertDpToPx(20f).toInt()
            accountsAdapter?.notifyDataSetChanged()
            this@AccountChooser.initialPosition = initialPosition
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    onPageSelectedListener?.invoke(position, accounts[position].name)
                }
            })
        }
    }

    fun setOnPageSelectedListener(callback: (Int, String) -> Unit) {
        onPageSelectedListener = callback
    }

    private fun updateAccounts() {
        accountsAdapter?.updateAccounts(accounts)
        if (initialPosition != -1) {
            accountsPager.setCurrentItem(initialPosition, false)
            pageIndicator.selection = initialPosition
            if (initialPosition == 0)
                onPageSelectedListener?.invoke(0, accounts[0].name)
            initialPosition = -1
        }
    }
}