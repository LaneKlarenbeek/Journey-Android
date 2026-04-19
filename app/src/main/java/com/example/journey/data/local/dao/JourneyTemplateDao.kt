package com.example.journey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.journey.data.local.entity.JourneyTemplate
import com.example.journey.data.local.entity.JourneyTemplateWithStops
import com.example.journey.data.local.entity.StopTemplate

@Dao
interface JourneyTemplateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJourneyTemplate(journeyTemplate: JourneyTemplate): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStopTemplate(stops: List<StopTemplate>)

    @Transaction
    @Query("SELECT * FROM journey_templates")
    suspend fun getJourneyTemplatesWithStops(): List<JourneyTemplateWithStops>

    @Query("SELECT * FROM stop_templates")
    suspend fun getAllStopsForAllJourneyTemplates(): List<StopTemplate>

}