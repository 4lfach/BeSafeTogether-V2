package com.selftutor.besafetogether.screens.profile.signup

import android.text.Editable
import android.util.Patterns
import androidx.lifecycle.*
import com.selftutor.besafetogether.data.api.request.RegisterRequest
import com.selftutor.besafetogether.data.api.response.BaseResponse
import com.selftutor.besafetogether.data.api.response.DefaultResponse
import com.selftutor.besafetogether.data.repository.UsersRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UsersRepository) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _registerEnabled = MediatorLiveData<Boolean>()
    val registerEnabled: LiveData<Boolean> = _registerEnabled
    var errorCounter = 0

    //Error in fields
    private val _actionUsernameError = MutableLiveData<String?>()
    val actionUsernameError: LiveData<String?> = _actionUsernameError

    private val _actionEmailError = MutableLiveData<String?>()
    val actionEmailError: LiveData<String?> = _actionEmailError

    private val _actionPhoneError = MutableLiveData<String?>()
    val actionPhoneError: LiveData<String?> = _actionPhoneError

    private val _actionPasswordError = MutableLiveData<String?>()
    val actionPasswordError: LiveData<String?> = _actionPasswordError

    private val _actionConfirmPasswordError = MutableLiveData<String?>()
    val actionConfirmPasswordError: LiveData<String?> = _actionConfirmPasswordError

    val registerResult: MutableLiveData<BaseResponse<DefaultResponse>> = MutableLiveData()

    init {
        _registerEnabled.addSource(_email) { checkError() }
        _registerEnabled.addSource(_password) { checkError() }
        _registerEnabled.addSource(_phone) { checkError() }
        _registerEnabled.addSource(_username) { checkError() }
        _registerEnabled.addSource(_confirmPassword) { checkError() }
    }

    fun onRegisterUser() {
        val username = "Test123"
        val email = "test@mail.com"
        val phone = "1234567890"
        val password = "123456"

        registerResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val request = RegisterRequest(username, email, phone, password)
                val response = repository.registerUser(request)

                if (response?.code() == 200) {
                    registerResult.value = BaseResponse.Success(response.body())
                } else {
                    registerResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                registerResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun setUsername(s: Editable) {
        val value = s.toString()
        _username.value = s.toString()
        if (value.isEmpty() || value.length < 6) {
            _actionUsernameError.value = "Username should be 6 characters long"
            errorCounter++
            return
        }
        errorCounter = 0
    }

    fun setEmail(s: Editable) {
        val value = s.toString()
        _email.value = value
        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            _actionEmailError.value = "Email is invalid or is empty"
            errorCounter++
            return
        }
        errorCounter = 0
    }

    fun setPhone(s: Editable) {
        val value = s.toString()
        _phone.value = value
        if (value.length < 9 || value.length > 12) {
            _actionPhoneError.value = "Phone number invalid"
            errorCounter++
            return
        }
        errorCounter = 0
    }

    fun setPassword(s: Editable) {
        val value = s.toString()
        _password.value = value
        if (value.length < 6 || value.length > 30) {
            _actionPasswordError.value =
                "Password must be longer than 6 characters and less than 30"
            errorCounter++
            return
        }
        errorCounter = 0
    }

    fun setConfirmPassword(s: Editable) {
        val value = s.toString()
        _confirmPassword.value = value
        if (value.length != _password.value?.length) {
            _actionConfirmPasswordError.value = "Passwords do not match"
            errorCounter++
            return
        }
        errorCounter = 0
    }

    private fun checkError() {
        if (errorCounter == 0) {
            _registerEnabled.value = true
            return
        }

        _registerEnabled.value = false
    }

}

