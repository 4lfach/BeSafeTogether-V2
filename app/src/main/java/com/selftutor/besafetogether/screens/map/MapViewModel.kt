package com.selftutor.besafetogether.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.selftutor.besafetogether.data.model.map.DangerPlace
import com.selftutor.besafetogether.data.api.map.PlaceFaker

class MapViewModel(
    placeFaker: PlaceFaker
) : ViewModel(){

    private val _dangerPlaces = MutableLiveData<List<DangerPlace>>()
    val dangerPlaces : LiveData<List<DangerPlace>> = _dangerPlaces

    private val _policePlaces = MutableLiveData<List<LatLng>>()
    val policePlaces : LiveData<List<LatLng>> = _policePlaces

    private val _drugStorePlaces = MutableLiveData<List<LatLng>>()
    val drugStorePlaces : LiveData<List<LatLng>> = _drugStorePlaces


    init{
        _dangerPlaces.value = placeFaker.dangerPlaces
        _policePlaces.value = placeFaker.policePlaces
        _drugStorePlaces.value = placeFaker.drugStorePlaces
    }
}

