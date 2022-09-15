package com.selftutor.besafetogether.model.database.stopwords

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StopWordsDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(stopWord: StopWord)

	@Delete
	suspend fun delete(stopWord: StopWord)

	@Query("SELECT * FROM stopWordsTable order by id ASC")
	fun getAllStopWords(): LiveData<List<StopWord>>

}