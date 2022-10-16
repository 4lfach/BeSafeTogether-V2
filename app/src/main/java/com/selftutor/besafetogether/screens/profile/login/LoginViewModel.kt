package com.selftutor.besafetogether.screens.profile.login

import android.text.Editable
import android.util.Patterns
import androidx.lifecycle.*
import com.selftutor.besafetogether.data.api.request.LoginRequest
import com.selftutor.besafetogether.data.api.response.BaseResponse
import com.selftutor.besafetogether.data.api.response.LoginResponse
import com.selftutor.besafetogether.data.repository.UsersRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsersRepository) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _actionEmailError = MutableLiveData<String?>()
    val actionEmailError: LiveData<String?> = _actionEmailError

    private val _actionPasswordError = MutableLiveData<String?>()
    val actionPasswordError: LiveData<String?> = _actionPasswordError

    private val _loginEnabled  = MediatorLiveData<Boolean>()
    val loginEnabled : LiveData<Boolean> = _loginEnabled

    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    private var errorCounter = 0

    init{
        _loginEnabled.addSource(_email){ checkError() }
        _loginEnabled.addSource(_password){ checkError() }
    }

    fun onLoginUser() {
        val pwd = _password.value!!
        val email = _email.value!!

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val loginRequest = LoginRequest(
                    password = pwd,
                    email = email
                )
                val response = repository.loginUser(request = loginRequest)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun setEmail(s: Editable){
        val value = s.toString()
        _email.value = value
        if(value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            _actionEmailError.value = "Email is invalid or is empty"
            errorCounter++
            return
        }
        errorCounter = 0
    }

    fun setPassword(s: Editable){
        val value = s.toString()
        _password.value = value
        if(value.length <6 || value.length > 30){
            _actionPasswordError.value = "Password must be longer than 6 characters and less than 30"
            errorCounter++
            return
        }
        errorCounter = 0
    }

    private fun checkError(){
        if(errorCounter == 0){
            _loginEnabled.value = true
            return
        }

        _loginEnabled.value = false
    }

}