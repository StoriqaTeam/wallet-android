package com.storiqa.storiqawallet.adapters

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.storiqa.storiqawallet.databinding.ItemContactBinding
import com.storiqa.storiqawallet.objects.Contact
import kotlinx.android.synthetic.main.item_contact.view.*
import org.jetbrains.anko.dip

class ContactsAdapter(private val contacts : Array<Contact>, val onClick : (position : Int) -> Unit) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    private fun getInflater(context : Context) : LayoutInflater {
        return layoutInflater?: LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(ItemContactBinding.inflate(getInflater(parent.context), parent, false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position], onClick)
    }

    class ViewHolder(private val binding : ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact, onClick : (position : Int) -> Unit) {
            binding.contact = contact
            binding.executePendingBindings()
            binding.root.ivContact.setImageURI(Uri.parse(contact.imageIri))
            binding.root.setOnClickListener { _ -> onClick(layoutPosition) }
        }

    }
}