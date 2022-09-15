package com.selftutor.besafetogether.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.selftutor.besafetogether.App
import com.selftutor.besafetogether.screens.profile.ProfileViewModel

class ViewModelFactory(
	private val app: App
): ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val viewModel = when(modelClass){
			ProfileViewModel::class.java->{
				ProfileViewModel(app.stopWordsRepo)
			}
			else -> {
				throw IllegalStateException("Unknown viewModel class")
			}
		}
		return viewModel as T
	}
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)