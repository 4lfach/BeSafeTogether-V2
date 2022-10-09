package com.selftutor.besafetogether.screens.profile

import androidx.lifecycle.*
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.data.model.stopword.StopWord
import com.selftutor.besafetogether.data.repository.StopWordsRepository
import com.selftutor.besafetogether.screens.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
	private val stopWordsRepository: StopWordsRepository
) : BaseViewModel(), StopWordsActionListener {

	var stopWords : LiveData<List<StopWord>> = stopWordsRepository.getAllStopWords()

	override fun onStopWordDelete(stopWord: StopWord) {
		viewModelScope.launch(Dispatchers.IO) {
			stopWordsRepository.delete(stopWord)
		}
	}

	override fun onStopWordAdd(stopWord: StopWord) {
		viewModelScope.launch(Dispatchers.IO) {
			if(!stopWordsRepository.insert(stopWord)){
				_actionShowToast.postValue(R.string.stop_word_exists)

				return@launch
			}
			_actionShowToast.postValue(R.string.stop_word_added)
		}
	}

}