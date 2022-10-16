package com.selftutor.besafetogether

import android.app.Application
import com.selftutor.besafetogether.data.api.map.PlaceFaker
import com.selftutor.besafetogether.data.SafeTogetherDb
import com.selftutor.besafetogether.data.repository.ContactsRepository
import com.selftutor.besafetogether.data.repository.StopWordsRepository
import com.selftutor.besafetogether.data.repository.UsersRepository

class App: Application(
) {
	private lateinit var database: SafeTogetherDb
	lateinit var stopWordsRepo : StopWordsRepository
	lateinit var contactsRepo: ContactsRepository

	lateinit var usersRepository: UsersRepository

	val placeFaker : PlaceFaker = PlaceFaker()

	override fun onCreate() {
		super.onCreate()

		database = SafeTogetherDb(this)
		stopWordsRepo = StopWordsRepository(database.getStopWordsDao())
		contactsRepo = ContactsRepository(database.getContactsDao())

		usersRepository = UsersRepository()
	}
}