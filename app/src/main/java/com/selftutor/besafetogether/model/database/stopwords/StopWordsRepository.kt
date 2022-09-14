package com.selftutor.besafetogether.model.database.stopwords

class StopWordsRepository(private val stopWordsDao: StopWordsDao) {

	val allStopWords = stopWordsDao.getAllStopWords()

	suspend fun insert(word: StopWord){
		stopWordsDao.insert(word)
	}

	suspend fun delete(word: StopWord){
		stopWordsDao.delete(word)
	}
}