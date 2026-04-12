package com.example.journey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.journey.data.local.entity.Journey
import com.example.journey.data.local.entity.Stop
import com.example.journey.data.local.dao.JourneyDao
import com.example.journey.data.local.dao.UserDao

@Database(
    entities = [
        Journey::class,
        Stop::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journeyDao(): JourneyDao
    abstract fun UserDao(): UserDao
}