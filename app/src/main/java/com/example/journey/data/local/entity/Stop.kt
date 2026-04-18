package com.example.journey.data.local.entity

import androidx.room.*
/*
@Entity(
    tableName = "stops",
    foreignKeys = [
        ForeignKey(
            entity = Journey::class,
            parentColumns = ["JourneyId"],
            childColumns = ["journeyOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["journeyOwnerId"])
    ]
)
data class Stop(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val journeyOwnerId: Long,
    val locationName: String,
    val sequenceOrder: Int,
)
*/