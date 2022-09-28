package com.selftutor.besafetogether.screens.profile.contacts

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.ContactItemBinding
import com.selftutor.besafetogether.databinding.PopupEditContactBinding
import com.selftutor.besafetogether.model.database.contacts.Contact


interface ContactActionListener {
    fun onUpdateContact(contact:Contact)

    fun onDeleteContact(contact: Contact)

    fun onInsertContact(contact: Contact)
}

class ContactsAdapter(
    private val actionListener: ContactActionListener,
    private val context: Context
): RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    var contacts = ArrayList<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        val name = contact.name
        val phone = contact.phone
        val gpsOn = contact.gpsOn

        with(holder.binding){
            nameTextView.text = name
            phoneTextView.text = phone
            gpsOnCheckBox.isChecked = gpsOn

            editImageView.setOnClickListener {
                showEditContactPopUp(contact , context)
            }

            deleteImageView.setOnClickListener {
                actionListener.onDeleteContact(contact)
            }
        }
    }

    override fun getItemCount(): Int = contacts.size

    fun updateList(newList: List<Contact>){
        contacts.clear()
        contacts.addAll(newList)

        notifyDataSetChanged()
    }

    private fun showEditContactPopUp(contact: Contact, context: Context) {
        val dialog = Dialog(context)
        val binding: PopupEditContactBinding = PopupEditContactBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        with(binding){
            nameEditText.setText(contact.name)
            phoneEditText.setText(contact.phone)
            gpsOnSwitch.isChecked = contact.gpsOn

            cancelButton.setOnClickListener {
                dialog.dismiss()
            }

            saveContactButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val phone = phoneEditText.text.toString()
                val gpsOn = gpsOnSwitch.isChecked

                var error = false
                if(name.isEmpty()){
                    nameEditText.error = context.getString(R.string.name_empty)
                    error = true
                }
                if(phone.isEmpty()){
                    phoneEditText.error = context.getString(R.string.phone_empty)
                    error = true
                }
                if(!error) {
                    val newContact = Contact(name, phone, gpsOn)
                    actionListener.onUpdateContact(newContact)
                    notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    class ViewHolder(val binding: ContactItemBinding): RecyclerView.ViewHolder(binding.root)
}

