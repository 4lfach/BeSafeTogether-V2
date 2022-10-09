package com.selftutor.besafetogether.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selftutor.besafetogether.data.model.DangerPlace
import com.selftutor.besafetogether.data.api.map.PlaceFaker

class MapViewModel(
    placeFaker: PlaceFaker
) : ViewModel(){

    private val _places = MutableLiveData<List<DangerPlace>>()
    val places : LiveData<List<DangerPlace>> = _places

    init{
        _places.value = placeFaker.dangerPlaces
    }
}

