package com.selftutor.besafetogether.screens.profile

import android.util.Log
import androidx.lifecycle.*
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.model.database.stopwords.StopWord
import com.selftutor.besafetogether.model.database.stopwords.StopWordsRepository
import com.selftutor.besafetogether.screens.BaseViewModel
import com.selftutor.besafetogether.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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