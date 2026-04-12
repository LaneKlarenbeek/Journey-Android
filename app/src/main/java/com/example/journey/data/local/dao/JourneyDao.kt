package com.example.journey.data.local.dao

import androidx.room.*
import com.example.journey.data.local.entity.Journey
import com.example.journey.data.local.entity.Stop
import com.example.journey.data.local.entity.JourneyWithStops


@Dao
interface JourneyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJourney(journey: Journey): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStops(stop: List<Stop>)

    @Transaction
    suspend fun insertJourneyWithStops(journey: Journey, stops: List<Stop>) {
        val newJourneyId = insertJourney(journey)

        val stopsWithId = stops.map { it.copy(journeyOwnerId = newJourneyId)}
        insertStops(stopsWithId)
    }

    @Transaction
    @Query("SELECT * FROM journeys")
    suspend fun getJourneysWithStops(): List<JourneyWithStops>

    @Transaction
    @Query("SELECT * FROM journeys WHERE id = :journeyId")
    suspend fun getJourneyWithStopsById(journeyId: Long): JourneyWithStops?
}