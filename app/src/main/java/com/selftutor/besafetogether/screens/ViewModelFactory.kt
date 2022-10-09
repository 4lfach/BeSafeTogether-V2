package com.selftutor.besafetogether.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.selftutor.besafetogether.App
import com.selftutor.besafetogether.screens.home.HomeViewModel
import com.selftutor.besafetogether.screens.map.MapViewModel
import com.selftutor.besafetogether.screens.profile.ProfileViewModel
import com.selftutor.besafetogether.screens.profile.contacts.ContactsViewModel
import com.selftutor.besafetogether.screens.profile.login.LoginViewModel
import com.selftutor.besafetogether.screens.profile.signup.SignupViewModel

class ViewModelFactory(
	private val app: App
): ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val viewModel = when(modelClass){
			ProfileViewModel::class.java->{
				ProfileViewModel(app.stopWordsRepo)
			}
			ContactsViewModel::class.java->{
				ContactsViewModel(app.contactsRepo)
			}
			MapViewModel::class.java->{
				MapViewModel(app.placeFaker)
			}
			HomeViewModel::class.java->{
				HomeViewModel(app.contactsRepo, app.stopWordsRepo)
			}
			SignupViewModel::class.java->{
				SignupViewModel()
			}
			LoginViewModel::class.java->{
				LoginViewModel(app.usersRepository)
			}
			else -> {
				throw IllegalStateException("Unknown viewModel class")
			}
		}
		return viewModel as T
	}
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)