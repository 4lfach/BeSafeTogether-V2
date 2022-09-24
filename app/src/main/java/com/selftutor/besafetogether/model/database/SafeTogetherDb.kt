package com.selftutor.besafetogether.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.selftutor.besafetogether.model.database.contacts.Contact
import com.selftutor.besafetogether.model.database.contacts.ContactDao
import com.selftutor.besafetogether.model.database.stopwords.StopWord
import com.selftutor.besafetogether.model.database.stopwords.StopWordsDao

@Database(entities = [StopWord::class, Contact::class], version = 1)
abstract class SafeTogetherDb() : RoomDatabase() {

	abstract fun getStopWordsDao(): StopWordsDao
	abstract fun getContactsDao(): ContactDao

	companion object {
		@Volatile
		private var instance: SafeTogetherDb? = null
		private val LOCK = Any()

		operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
			instance ?: createDatabase(context).also{
				instance = it
			}
		}

		private fun createDatabase(context: Context) =
			Room.databaseBuilder(context.applicationContext, SafeTogetherDb::class.java, "SafeTogether.db" )
				.allowMainThreadQueries()
				.fallbackToDestructiveMigrationOnDowngrade()
				.build()
	}
}