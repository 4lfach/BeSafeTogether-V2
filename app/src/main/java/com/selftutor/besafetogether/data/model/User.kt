package com.selftutor.besafetogether.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class User(
    var id: Int = 0,
    @SerializedName("profile_link") val profileLink: String,
    val username: String,
    val phone: String,
    val email: String
)