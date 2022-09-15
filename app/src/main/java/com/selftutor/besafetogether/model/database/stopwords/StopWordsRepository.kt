package com.selftutor.besafetogether.model.database.stopwords

import androidx.lifecycle.LiveData
import com.selftutor.besafetogether.model.database.SafeTogetherDb

class StopWordsRepository(private val db: SafeTogetherDb) {

	suspend fun insert(word: StopWord) = db.getStopWordsDao().insert(word)

	suspend fun delete(word: StopWord) = db.getStopWordsDao().delete(word)

	fun getAllStopWords() = db.getStopWordsDao().getAllStopWords()

	suspend fun insertStopWords(words: List<StopWord>) {
		for (word  in words){
			db.getStopWordsDao().insert(word)
		}
	}
}