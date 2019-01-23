package com.storiqa.storiqawallet.ui.main.wallet

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentWalletBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment

class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel>() {

    private lateinit var adapter: AccountsAdapter

    override fun getLayoutId(): Int = R.layout.fragment_wallet

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<WalletViewModel> = WalletViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        viewModel.updateAccounts.observe(this, Observer {
            adapter.updateAccounts(viewModel.cards)
        })
    }

    private fun initView() {
        adapter = AccountsAdapter(viewModel.cards)

        binding.recyclerView.layoutManager = StackCardLayoutManager()
        binding.recyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
                    override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                    ): Boolean = true.also { _ ->
                        val fromPos = viewHolder.adapterPosition
                        val toPos = target.adapterPosition
                        adapter.notifyItemMoved(fromPos, toPos)
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    }
                })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        /*val adapter = AccountsAdapter(viewModel.accounts, {})
        recyclerView.adapter = adapter

        recyclerView.layoutManager = StackCardLayoutManager()

        val itemTouchHelper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
                    override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                    ): Boolean = true.also { _ ->
                        val fromPos = viewHolder.adapterPosition
                        val toPos = target.adapterPosition
                        adapter.notifyItemMoved(fromPos, toPos)
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    }
                })
        itemTouchHelper.attachToRecyclerView(recyclerView)*/
    }
}