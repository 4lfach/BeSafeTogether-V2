package com.selftutor.besafetogether.data.model.stopword

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StopWordsDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(stopWord: StopWord)

	@Delete
	suspend fun delete(stopWord: StopWord)

	@Query("SELECT * FROM stopWords order by id ASC")
	fun getAllStopWords(): LiveData<List<StopWord>>

	@Query("SELECT EXISTS(SELECT * FROM stopWords WHERE word = :word)")
	suspend fun isWordExists(word:String): Boolean
}