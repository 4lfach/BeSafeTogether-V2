package com.selftutor.besafetogether.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel(){

    val _actionShowToast = MutableLiveData<Int>()
    val actionShowToast: LiveData<Int> = _actionShowToast
}