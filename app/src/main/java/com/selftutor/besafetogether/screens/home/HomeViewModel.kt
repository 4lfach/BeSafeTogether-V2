package com.selftutor.besafetogether.screens.home

import androidx.lifecycle.ViewModel
import com.selftutor.besafetogether.model.database.contacts.ContactsRepository
import com.selftutor.besafetogether.model.database.stopwords.StopWordsRepository

class HomeViewModel(private val contactsRepo: ContactsRepository,
private val stopWordsRepo: StopWordsRepository) : ViewModel(), OnHomeActionListener {

}

interface OnHomeActionListener {

}
