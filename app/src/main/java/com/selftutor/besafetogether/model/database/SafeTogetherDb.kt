package com.selftutor.besafetogether.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.selftutor.besafetogether.model.database.stopwords.StopWord
import com.selftutor.besafetogether.model.database.stopwords.StopWordsDao

@Database(entities = [StopWord::class], version = 2, exportSchema = false)
abstract class SafeTogetherDb() : RoomDatabase() {

	abstract fun getStopWordsDao(): StopWordsDao

	companion object {
		@Volatile
		private var INSTANCE: SafeTogetherDb? = null

		fun getDatabase(context: Context): SafeTogetherDb {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					SafeTogetherDb::class.java,
					"safeTogetherDb").build()
				INSTANCE = instance

				instance
			}

		}
	}
}