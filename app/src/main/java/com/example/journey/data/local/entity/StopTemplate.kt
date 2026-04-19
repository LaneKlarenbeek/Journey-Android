package com.example.journey.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "stop_templates",
    foreignKeys = [
        ForeignKey(
            entity = JourneyTemplate::class,
            parentColumns = ["templateId"],
            childColumns = ["templateOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("templateId")]
)
data class StopTemplate(
    @PrimaryKey(autoGenerate = true) val stopTemplateId: Long = 0,
    val templateOwnerId: Long,
    val locationName: String,
    val  sequenceOrder: Int
)
