package com.selftutor.besafetogether.model.database.stopwords

import androidx.lifecycle.LiveData
import com.selftutor.besafetogether.model.database.SafeTogetherDb

class StopWordsRepository(private val dao: StopWordsDao) {

	suspend fun insert(stopWord: StopWord): Boolean {
		if(isWordExists(stopWord.word)){
			return false
		}
		dao.insert(stopWord)
		return true
	}


	suspend fun delete(word: StopWord) = dao.delete(word)

	fun getAllStopWords() = dao.getAllStopWords()

	suspend fun isWordExists(word: String) = dao.isWordExists(word)
}