package com.example.journey.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journey_templates")
data class JourneyTemplate(
    @PrimaryKey(autoGenerate = true) val templateId: Long = 0,
    val title: String,
)
