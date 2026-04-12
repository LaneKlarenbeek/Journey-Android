package com.example.journey.data.local.entity

import androidx.room.*

@Entity(tableName = "journeys")
data class Journey(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val startDateTimeStamp: Long,
)

