package com.selftutor.besafetogether.data.model.map

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") var id: String,
    @SerializedName("profile_link") val profileLink: String,
    @SerializedName("username") val username: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String
)