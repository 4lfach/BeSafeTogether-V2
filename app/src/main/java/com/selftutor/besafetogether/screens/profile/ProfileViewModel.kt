package com.selftutor.besafetogether.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selftutor.besafetogether.model.database.stopwords.StopWord
import com.selftutor.besafetogether.model.database.stopwords.StopWordsRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
	val stopWordsRepository: StopWordsRepository
) : ViewModel(), StopWordsActionListener {

	private val _stopWords = MutableLiveData<List<StopWord>>()
	val stopWords: LiveData<List<StopWord>> = _stopWords

	private val _actionShowToast = MutableLiveData<Int>()
	val actionShowToast: LiveData<Int> = _actionShowToast

	init{
		_stopWords.value = stopWordsRepository.allStopWords
	}

	override fun onUserDelete(stopWord: StopWord) {
		viewModelScope.launch {
			stopWordsRepository.delete(stopWord)
		}
	}

	override fun onUserAdd(stopWord: StopWord) {
		viewModelScope.launch {
			stopWordsRepository.insert(stopWord)
		}
	}

}