package com.example.journey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Delete
import com.example.journey.data.local.entity.JourneyRecord
import com.example.journey.data.local.entity.JourneyRecordWithDetails
import com.example.journey.data.local.entity.StopRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface JourneyRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJourneyRecord(journeyRecord: JourneyRecord): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStopRecords(stops: List<StopRecord>)

    @Update
    suspend fun updateStopRecord(stop: StopRecord)

    @Update
    suspend fun updateJourneyRecord(journey: JourneyRecord)

    @Delete
    suspend fun deleteJourneyRecord(journey: JourneyRecord)

    @Transaction
    @Query("SELECT * FROM journey_records WHERE journeyId = :journeyId")
    fun getActiveJourneyById(journeyId: Long): Flow<JourneyRecordWithDetails?>
}