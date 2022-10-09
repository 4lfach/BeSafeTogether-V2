package com.selftutor.besafetogether.data.api.response

import com.selftutor.besafetogether.data.model.User
import java.io.Serializable

data class LoginResponse(
    val user: User,
    val error: String
): Serializable