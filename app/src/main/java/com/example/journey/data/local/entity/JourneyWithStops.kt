package com.example.journey.data.local.entity

import androidx.room.*

data class JourneyWithStops(
    @Embedded val journey: Journey,

    @Relation(
        parentColumn = "journeyId",
        entityColumn = "journeyOwnerId"
    )
    val stops: List<Stop>
)