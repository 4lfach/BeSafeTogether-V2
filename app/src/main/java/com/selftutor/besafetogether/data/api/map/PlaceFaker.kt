package com.selftutor.besafetogether.data.api.map

import com.github.javafaker.Faker
import com.google.android.gms.maps.model.LatLng
import com.selftutor.besafetogether.data.model.map.DangerPlace

class PlaceFaker {

    var dangerPlaces: MutableList<DangerPlace> = mutableListOf()
    var policePlaces: MutableList<LatLng> = latLngPoliceList
    var drugStorePlaces: MutableList<LatLng> = latLngDrugStoreList

    private fun loadPlaces(){

        val faker = Faker.instance()

        for(latLng in latLngList){
            val address = faker.address().streetAddress().toString()
            val comments = CommentFaker().loadComments(latLng)
            var averageRating = 0F
            for (com in comments){
                averageRating += com.rating
            }
            averageRating /= comments.size

            dangerPlaces.add(DangerPlace(latLng,address, averageRating, comments))
        }
    }

    init {
        loadPlaces()
    }

    companion object{
        val latLngList = mutableListOf<LatLng>(
            LatLng(51.131511, 71.365237),
            LatLng(51.142560, 71.372471),
            LatLng(51.151077, 71.372286),
            LatLng(51.173421, 71.391669),
            LatLng(51.173722, 71.429418),
            LatLng(51.162203, 71.440663))
        val latLngPoliceList = mutableListOf<LatLng>(
            LatLng(51.158372, 71.480095),
            LatLng(51.152447, 71.462386),
            LatLng(51.167495, 71.434657),
            LatLng(51.116520, 71.417798),
            LatLng(51.088515, 71.421574)
        )

        val latLngDrugStoreList = mutableListOf<LatLng>(
            LatLng(51.124309, 71.417958),
            LatLng(51.128948, 71.437003),
            LatLng(51.116794, 71.439049),
            LatLng(51.115547, 71.430966),
            LatLng(51.115329, 71.420580),
            LatLng(51.114852, 71.413668)
        )
    }

}