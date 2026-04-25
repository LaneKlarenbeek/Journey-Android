package com.example.journey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.journey.data.local.dao.JourneyRecordDao
import com.example.journey.data.local.dao.JourneyTemplateDao
import com.example.journey.data.local.dao.UserDao
import com.example.journey.data.local.entity.JourneyTemplate
import com.example.journey.data.local.entity.StopTemplate
import com.example.journey.data.local.entity.User
import com.example.journey.data.local.entity.JourneyRecord
import com.example.journey.data.local.entity.StopRecord
import com.example.journey.data.local.entity.NoteRecord

@Database(
    entities = [
        User::class,
        JourneyTemplate::class,
        StopTemplate::class,
        JourneyRecord::class,
        StopRecord::class,
        NoteRecord::class
    ],
    version = 3,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun journeyTemplateDao(): JourneyTemplateDao
    abstract fun journeyRecordDao(): JourneyRecordDao
}