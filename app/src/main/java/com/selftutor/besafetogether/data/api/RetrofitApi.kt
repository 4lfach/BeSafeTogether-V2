package com.selftutor.besafetogether.data.api

import com.selftutor.besafetogether.data.api.request.LoginRequest
import com.selftutor.besafetogether.data.api.request.RegisterRequest
import com.selftutor.besafetogether.data.api.response.DefaultResponse
import com.selftutor.besafetogether.data.api.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitApi {

    @POST("user/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("user/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<DefaultResponse>

    companion object {
        fun getApi(): RetrofitApi? {
            return ApiClient.client?.create(RetrofitApi::class.java)
        }
    }
}