package com.selftutor.besafetogether.model.database.contacts

import androidx.lifecycle.LiveData

class ContactsRepository(private val dao: ContactDao) {

    suspend fun delete(contact: Contact){
        dao.delete(contact)
    }

    suspend fun update(contact: Contact){
        dao.update(contact)
    }

    suspend fun insert(contact: Contact) : Boolean{
        if(isPhoneExists(contact.phone)){
            return false
        }
        dao.insert(contact)
        return true
    }

    suspend fun isPhoneExists(phone: String): Boolean = dao.isPhoneExists(phone)

    fun getAllContacts(): LiveData<List<Contact>> = dao.getAllContacts()
}