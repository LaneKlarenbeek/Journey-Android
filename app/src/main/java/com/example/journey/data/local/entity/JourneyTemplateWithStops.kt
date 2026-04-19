package com.example.journey.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class JourneyTemplateWithStops(
    @Embedded val template: JourneyTemplate,

    @Relation(
        parentColumn = "templateId",
        entityColumn = "templateOwnerId"
    )
    val stops: List<StopTemplate>
)