package com.selftutor.besafetogether.data.api.map

import com.github.javafaker.Faker
import com.google.android.gms.maps.model.LatLng
import com.selftutor.besafetogether.data.model.DangerPlace

class PlaceFaker {

    var dangerPlaces: MutableList<DangerPlace> = mutableListOf()

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
    }

}