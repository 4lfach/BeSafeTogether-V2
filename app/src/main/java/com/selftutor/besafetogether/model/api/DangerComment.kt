package com.selftutor.besafetogether.model.api

import com.google.android.gms.maps.model.LatLng

data class DangerComment(
    val id: Int,
    val user: User,
    val content: String,
    val latLng: LatLng,
    val rating: Float,
    val date: String
)
