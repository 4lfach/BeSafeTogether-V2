package com.selftutor.besafetogether.screens.profile.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.model.database.contacts.Contact
import com.selftutor.besafetogether.model.database.contacts.ContactsRepository
import com.selftutor.besafetogether.screens.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel(private val repository: ContactsRepository)
    : BaseViewModel(), ContactActionListener {

    val contacts : LiveData<List<Contact>> = repository.getAllContacts()

    override fun onUpdateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(contact)
        }
    }

    override fun onDeleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(contact)
        }
    }

    override fun onInsertContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            if(!repository.insert(contact)){
                _actionShowToast.postValue(R.string.contact_exists)

                return@launch
            }
            _actionShowToast.postValue(R.string.contact_added)

        }
    }
}