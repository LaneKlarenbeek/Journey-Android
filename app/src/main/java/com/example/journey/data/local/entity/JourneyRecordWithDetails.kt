package com.example.journey.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class JourneyRecordWithDetails(
    @Embedded val journey: JourneyRecord,

    @Relation(
        entity = StopRecord::class,
        parentColumn = "journeyId",
        entityColumn = "journeyOwnerId"
    )
    val stopsWithNotes: List<StopRecordWithNotes>
)