package com.selftutor.besafetogether.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selftutor.besafetogether.model.api.PlacesService
import com.selftutor.besafetogether.model.map.Place

class MapViewModel(service: PlacesService) : ViewModel(), OnMapActionListener {

    private val _dangerPlaces = MutableLiveData<List<Place>>()
    val dangerPlaces : LiveData<List<Place>> = _dangerPlaces

    init{
        _dangerPlaces.value = service.places
    }

    override fun onDangerPlace(place: Place) {
        TODO("Not yet implemented")
    }

}

interface OnMapActionListener {
    fun onDangerPlace(place: Place)
}
