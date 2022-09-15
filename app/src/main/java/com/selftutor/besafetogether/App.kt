package com.selftutor.besafetogether

import android.app.Application
import androidx.room.Database
import com.selftutor.besafetogether.model.database.SafeTogetherDb
import com.selftutor.besafetogether.model.database.stopwords.StopWordsDao
import com.selftutor.besafetogether.model.database.stopwords.StopWordsRepository

class App: Application(
) {
	private lateinit var database: SafeTogetherDb
	lateinit var stopWordsRepo : StopWordsRepository

	override fun onCreate() {
		super.onCreate()
		database = SafeTogetherDb(this)
		stopWordsRepo = StopWordsRepository(database)
	}
}