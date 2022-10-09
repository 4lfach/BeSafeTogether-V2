package com.selftutor.besafetogether.data.model

import com.google.android.gms.maps.model.LatLng

data class DangerPlace(
    val latLng: LatLng,
    val address: String?,
    val averageRating: Float,
    val comments: List<DangerComment>
)