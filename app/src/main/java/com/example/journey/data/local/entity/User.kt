package com.example.journey.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class User(
    @PrimaryKey val userId: Int = 1,
    val firstName: String,
    val lastName: String,
    val role: String,
    val joinDateTimeStamp: Long,
)