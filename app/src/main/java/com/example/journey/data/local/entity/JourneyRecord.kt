package com.example.journey.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journey_records")
data class JourneyRecord(
    @PrimaryKey(autoGenerate = true) val journeyId: Long = 0,
    val title: String,
    val startTimeStamp: Long, // Stamped the exact millisecond "Play" is clicked
    val endTimeStamp: Long? = null // Remains null until "End" is clicked
)