package com.selftutor.besafetogether.data.repository

import com.selftutor.besafetogether.data.api.ApiHelper

class UsersRepository(private val apiHelper: ApiHelper) {

    suspend fun login(email: String, password: String) = apiHelper.login(email, password)

    suspend fun signUp(username: String, email: String, phone: String, password: String) =
        apiHelper.signUp(username, email, phone, password)
}

