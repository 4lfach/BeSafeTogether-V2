package com.selftutor.besafetogether.data.repository

import com.selftutor.besafetogether.data.model.stopword.StopWord
import com.selftutor.besafetogether.data.model.stopword.StopWordsDao

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