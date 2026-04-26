package com.example.journey.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.journey.data.local.entity.NoteRecord
import kotlinx.coroutines.flow.Flow

@Dao // Required for Room to generate the implementation
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(newNote: NoteRecord)

    @Delete
    suspend fun deleteNote(note: NoteRecord)

    // Helper to get notes for a specific stop if needed
    @Query("SELECT * FROM note_records WHERE stopOwnerId = :stopId")
    fun getNotesForStop(stopId: Long): Flow<List<NoteRecord>>
}