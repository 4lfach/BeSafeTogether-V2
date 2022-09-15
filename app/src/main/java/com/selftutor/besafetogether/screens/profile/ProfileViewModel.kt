package com.selftutor.besafetogether.screens.profile

import android.util.Log
import androidx.lifecycle.*
import com.selftutor.besafetogether.model.database.stopwords.StopWord
import com.selftutor.besafetogether.model.database.stopwords.StopWordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileViewModel(
	private val stopWordsRepository: StopWordsRepository
) : ViewModel(), StopWordsActionListener {

	private val _stopWords = MutableLiveData<List<StopWord>>()
	var stopWords : LiveData<List<StopWord>> = _stopWords

	private val _actionShowToast = MutableLiveData<Int>()
	val actionShowToast: LiveData<Int> = _actionShowToast

	init{
		viewModelScope.launch(Dispatchers.IO) {
			testData()
			val words = stopWordsRepository.getAllStopWords().value
			_stopWords.value = words ?: null
		}
	}

	override fun onStopWordDelete(stopWord: StopWord) {
		viewModelScope.launch(Dispatchers.IO) {
			stopWordsRepository.delete(stopWord)
		}
	}

	override fun onStopWordAdd(stopWord: StopWord) {
		viewModelScope.launch(Dispatchers.IO) {
			stopWordsRepository.insert(stopWord)
		}
	}

	fun testData(){
		val stopwords = listOf<StopWord>(
			StopWord("", "smth"),
			StopWord("", "smth2"),
			StopWord("", "smth3"),
		)
		viewModelScope.launch { 
			stopWordsRepository.insertStopWords(stopwords)
		}
	}

}