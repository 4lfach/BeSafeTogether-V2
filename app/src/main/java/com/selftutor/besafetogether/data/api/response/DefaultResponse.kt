package com.selftutor.besafetogether.data.api.response

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("error") val error: String?,
    @SerializedName("message") val message: String)