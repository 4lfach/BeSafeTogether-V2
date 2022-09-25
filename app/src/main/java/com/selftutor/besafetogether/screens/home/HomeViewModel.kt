package com.selftutor.besafetogether.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selftutor.besafetogether.model.database.contacts.Contact
import com.selftutor.besafetogether.model.database.contacts.ContactsRepository
import com.selftutor.besafetogether.model.database.stopwords.StopWord
import com.selftutor.besafetogether.model.database.stopwords.StopWordsRepository

class HomeViewModel(contactsRepo: ContactsRepository,
stopWordsRepo: StopWordsRepository) : ViewModel() {

    private val _gpsIsOn = MutableLiveData<Boolean>()
    val gpsIsOn : LiveData<Boolean> = _gpsIsOn

    val contacts : LiveData<List<Contact>> = contactsRepo.getAllContacts()

    val stopWords: LiveData<List<StopWord>> = stopWordsRepo.getAllStopWords()

    private var _scanButtonEnabled = MediatorLiveData<Boolean>()
    val scanButtonEnabled : LiveData<Boolean> = _scanButtonEnabled

    init{
        _scanButtonEnabled.addSource(_gpsIsOn){
            _scanButtonEnabled.value = checkRequirements()
        }
        _scanButtonEnabled.addSource(contacts){
            _scanButtonEnabled.value = checkRequirements()
        }

        _scanButtonEnabled.addSource(stopWords){
            _scanButtonEnabled.value = checkRequirements()
        }
    }

    private fun checkRequirements(): Boolean{
        if(_gpsIsOn.value == true && contacts.value?.isNotEmpty() == true && stopWords.value?.isNotEmpty() == true){
            return true
        }
        return false
    }

    fun onGpsPermissionGranted(granted: Boolean) {
        _gpsIsOn.value = granted
    }

}



