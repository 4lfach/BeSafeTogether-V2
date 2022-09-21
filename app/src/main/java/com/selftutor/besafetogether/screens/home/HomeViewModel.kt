package com.selftutor.besafetogether.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selftutor.besafetogether.model.database.contacts.ContactsRepository
import com.selftutor.besafetogether.model.database.stopwords.StopWordsRepository

class HomeViewModel(private val contactsRepo: ContactsRepository,
private val stopWordsRepo: StopWordsRepository) : ViewModel() {

    private val _gpsIsOn = MutableLiveData<Boolean>()
    val gpsIsOn : LiveData<Boolean> = _gpsIsOn

    private val _contactsSet = MutableLiveData<Boolean>()
    val contactsSet : LiveData<Boolean> = _contactsSet

    private val _stopWordsSet = MutableLiveData<Boolean>()
    val stopWordsSet : LiveData<Boolean> = _stopWordsSet

    private var _buttonEnabled = MutableLiveData<Boolean>()
        set(value) {
            if(_contactsSet.value!! && gpsIsOn.value!! && _stopWordsSet.value!!){
                field.value = true
            }
            field.value = false
        }
    val scanButtonEnabled : LiveData<Boolean> = _buttonEnabled

    init {

    }


}

