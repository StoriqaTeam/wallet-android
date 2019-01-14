package com.storiqa.storiqawallet.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ItemContactBinding
import com.storiqa.storiqawallet.objects.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsAdapter(private val contacts: Array<Contact>, val onClick: (position: Int) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    private fun getInflater(context: Context): LayoutInflater {
        return layoutInflater ?: LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(ItemContactBinding.inflate(getInflater(parent.context), parent, false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position], onClick)
    }

    class ViewHolder(private val binding: ItemContactBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact, onClick: (position: Int) -> Unit) {
            binding.apply {
                this.contact = contact
                executePendingBindings()
                root.tvNameShorten.text = contact.getShortenName()
                root.setOnClickListener { _ -> onClick(layoutPosition) }
                if (contact.imageIri.isNotEmpty()) {
                    root.tvNameShorten.visibility = View.GONE
                    root.ivContact.setImageURI(Uri.parse(contact.imageIri))
                } else {
                    root.tvNameShorten.visibility = View.VISIBLE
                    root.ivContact.setImageResource(R.color.contactGray)
                }
                //root.setPadding(root.context.dip(17), 0, root.context.dip(17), 0)
            }
        }
    }
}