package com.selftutor.besafetogether.screens.profile.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel(): ViewModel() {

    private val _username = MutableLiveData<String>()
    val username : LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _phone = MutableLiveData<String>()
    val phone : LiveData<String> = _phone

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword : LiveData<String> = _confirmPassword

    var canRegister: Boolean = false

    fun registerUser(): Boolean{
        if(canRegister){
            return true
        }

        return false
    }
}

