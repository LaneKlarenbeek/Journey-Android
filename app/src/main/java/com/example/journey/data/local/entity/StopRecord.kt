package com.example.journey.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "stop_records",
    foreignKeys = [
        ForeignKey(
            entity = JourneyRecord::class,
            parentColumns = ["journeyId"],
            childColumns = ["journeyOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["journeyOwnerId"])]
)
data class StopRecord(
    @PrimaryKey(autoGenerate = true) val stopId: Long = 0,
    val journeyOwnerId: Long,
    val locationName: String,
    val sequenceOrder: Int,
    val timeStamp: Long? = null,
)
