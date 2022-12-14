package com.selftutor.besafetogether

import android.app.Application
import com.selftutor.besafetogether.model.database.SafeTogetherDb
import com.selftutor.besafetogether.model.database.contacts.ContactsRepository
import com.selftutor.besafetogether.model.database.stopwords.StopWordsRepository

class App: Application(
) {
	private lateinit var database: SafeTogetherDb
	lateinit var stopWordsRepo : StopWordsRepository
	lateinit var contactsRepo: ContactsRepository


	override fun onCreate() {
		super.onCreate()
		database = SafeTogetherDb(this)
		stopWordsRepo = StopWordsRepository(database.getStopWordsDao())
		contactsRepo = ContactsRepository(database.getContactsDao())

	}
}