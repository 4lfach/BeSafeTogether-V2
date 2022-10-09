package com.selftutor.besafetogether.screens.profile.contacts

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentContactsBinding
import com.selftutor.besafetogether.databinding.PopupEditContactBinding
import com.selftutor.besafetogether.data.model.contact.Contact
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory

class ContactsFragment: BaseFragment() {
	private val viewModel: ContactsViewModel by viewModels { factory() }

	private lateinit var binding: FragmentContactsBinding
	private lateinit var adapter: ContactsAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentContactsBinding.inflate(inflater, container, false)
		adapter = ContactsAdapter(viewModel, requireContext())
		val layoutManager = LinearLayoutManager(requireContext())

		viewModel.actionShowToast.observe(viewLifecycleOwner){
			showToast(it)
		}

		viewModel.contacts.observe(viewLifecycleOwner){
			it.let{
				adapter.updateList(it)
			}
		}

		with(binding){
			contactsRecyclerView.adapter = adapter
			contactsRecyclerView.layoutManager = layoutManager

			addContactButton.setOnClickListener {
				showAddContactPopUp()
			}

			goBackImageView.setOnClickListener {
				findNavController().popBackStack()
			}
		}

		return binding.root
	}

	private fun showAddContactPopUp() {
		val dialog = Dialog(requireContext())
		val binding: PopupEditContactBinding = PopupEditContactBinding.inflate(LayoutInflater.from(context))
		dialog.setContentView(binding.root)
		dialog.setCancelable(false)
		dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

		with(binding){
			saveContactButton.setOnClickListener {
				val name = nameEditText.text.toString()
				val phone = phoneEditText.text.toString()
				val gpsOn = gpsOnSwitch.isChecked

				var error = false
				if(name.isEmpty()){
					nameEditText.error = requireContext().getString(R.string.name_empty)
					error = true
				}
				if(phone.isEmpty()){
					phoneEditText.error = requireContext().getString(R.string.phone_empty)
				}
				if(!error){
					val contact = Contact(name, phone, gpsOn)
					viewModel.onInsertContact(contact)
					dialog.dismiss()
				}
			}

			cancelButton.setOnClickListener {
				dialog.dismiss()
			}
		}
		dialog.show()
	}
}