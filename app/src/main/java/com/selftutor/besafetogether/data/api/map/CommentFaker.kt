package com.selftutor.besafetogether.data.api.map

import com.github.javafaker.Faker
import com.google.android.gms.maps.model.LatLng
import com.selftutor.besafetogether.data.model.DangerComment
import com.selftutor.besafetogether.data.api.user.UserFaker
import java.util.*

class CommentFaker {

    fun loadComments(latLng: LatLng): List<DangerComment>{
        val faker = Faker.instance()

        val users = UserFaker().users

        val comments = (0 until users.size).map {
            DangerComment(
            id = it,
            user =  users[it],
            content = faker.lorem().sentences(2).toString(),
            latLng = latLng,
            rating = faker.number().numberBetween(1, 5).toFloat(),
            date = faker.date().between(Date(2022, 7, 1), Date(2022, 9, 28)).toString()

        )
        } as MutableList<DangerComment>

        return comments
    }
}