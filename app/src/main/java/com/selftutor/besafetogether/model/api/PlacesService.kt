package com.selftutor.besafetogether.model.api

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.selftutor.besafetogether.model.map.Place

class PlacesService {

    val places = listOf(
        Place(name = "Ulitsa", latLng = LatLng(51.142712, 71.371998), address = "Syganak 4"),
        Place(name = "Ulitsa", latLng = LatLng(51.148498, 71.385540), address = "Syganak 5"),
        Place(name = "Ulitsa", latLng = LatLng(51.146651, 71.432541), address = "Syganak 6"),
        Place(name = "Ulitsa", latLng = LatLng(51.156578, 71.412866), address = "Syganak 7"),
    )
}