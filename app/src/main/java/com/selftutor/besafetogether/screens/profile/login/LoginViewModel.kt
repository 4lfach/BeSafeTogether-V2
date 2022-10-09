package com.selftutor.besafetogether.screens.profile.login

import androidx.lifecycle.*
import com.selftutor.besafetogether.data.repository.UsersRepository
import com.selftutor.besafetogether.utils.Resource
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: UsersRepository) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    var canLogin = false

    fun onLogin() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.login(_email.value!!, _password.value!!)))
        } catch (e: Exception){
            emit(Resource.error(data = null, message = e.message ?: "error occurred"))
        }
    }
}