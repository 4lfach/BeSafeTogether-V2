package com.selftutor.besafetogether.data.api

class ApiHelper(private val api: RetrofitApi) {

    fun login(email: String, password: String) = api.loginUser(email, password)

    fun signUp(username: String, email: String, phone: String, password: String) =
        api.createUser(username, email, phone, password)
}