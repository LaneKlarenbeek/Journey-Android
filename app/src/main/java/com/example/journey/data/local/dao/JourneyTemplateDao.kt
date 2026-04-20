package com.example.journey.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.journey.data.local.entity.JourneyTemplate
import com.example.journey.data.local.entity.JourneyTemplateWithStops
import com.example.journey.data.local.entity.StopTemplate
import kotlinx.coroutines.flow.Flow

@Dao
interface JourneyTemplateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJourneyTemplate(journeyTemplate: JourneyTemplate): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStopTemplate(stops: List<StopTemplate>)

    @Transaction
    @Query("SELECT * FROM journey_templates")
    fun getJourneyTemplatesWithStops(): Flow<List<JourneyTemplateWithStops>>

    @Query("SELECT * FROM stop_templates")
    suspend fun getAllStopsForAllJourneyTemplates(): List<StopTemplate>
    @Delete
    suspend fun deleteJourneyTemplate(journeyTemplate: JourneyTemplate)
}