package com.selftutor.besafetogether.model.api

data class User(
    var id: Int = 0,
    val profileLink: String,
    val username: String,
    val phone: String,
    val email: String,
)