package com.storiqa.storiqawallet.ui.main.wallet

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.databinding.ItemAccountBinding

class AccountsAdapter(private var accounts: List<AccountEntity>, private var rates: List<RateEntity>) :
        RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    private val currencyFiat = "USD"

    fun updateAccounts(newAccounts: List<AccountEntity>, newRates: List<RateEntity>) {
        accounts = newAccounts
        rates = newRates
        notifyDataSetChanged()
    }

    private var layoutInflater: LayoutInflater? = null

    private fun getInflater(context: Context): LayoutInflater {
        return layoutInflater ?: LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemAccountBinding.inflate(getInflater(parent.context), parent, false))

    override fun getItemCount(): Int = accounts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accounts[position]
        var rate = RateEntity("", "", 0.0)
        for (r in rates) {
            if (r.currencyCrypto.equals(account.currency, true) && r.currencyFiat.equals(currencyFiat, true)) {
                rate = r
                break
            }
        }
        holder.bind(account, rate, currencyFiat)
    }

    class ViewHolder(private val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(account: AccountEntity, rate: RateEntity, currencyFiat: String) {

            binding.apply {
                this.currencyFiat = currencyFiat
                this.account = account
                this.rate = rate
                executePendingBindings()
                //root.setOnClickListener { _ -> onClick(layoutPosition) }

                //val billInfo = BillInfo(bill)
                //billInfo.initBillView(root)
            }
        }
    }

}