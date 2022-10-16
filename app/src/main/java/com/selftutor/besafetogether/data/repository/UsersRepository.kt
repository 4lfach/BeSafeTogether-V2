package com.selftutor.besafetogether.data.repository

import com.selftutor.besafetogether.data.api.RetrofitApi
import com.selftutor.besafetogether.data.api.request.LoginRequest
import com.selftutor.besafetogether.data.api.request.RegisterRequest
import com.selftutor.besafetogether.data.api.response.DefaultResponse
import com.selftutor.besafetogether.data.api.response.LoginResponse
import retrofit2.Response

class UsersRepository{

    suspend fun registerUser(request: RegisterRequest): Response<DefaultResponse>?{
        return RetrofitApi.getApi()?.registerUser(registerRequest = request)
    }

    suspend fun loginUser(request: LoginRequest) : Response<LoginResponse>?{
        return RetrofitApi.getApi()?.loginUser(loginRequest = request)
    }
}

