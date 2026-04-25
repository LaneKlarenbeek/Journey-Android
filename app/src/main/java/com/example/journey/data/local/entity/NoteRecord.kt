package com.example.journey.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "note_records",
    foreignKeys = [
        ForeignKey(
            entity = StopRecord::class,
            parentColumns = ["stopId"],
            childColumns = ["stopOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["stopOwnerId"])]
)
data class NoteRecord(
    @PrimaryKey(autoGenerate = true) val noteId: Long = 0,
    val stopOwnerId: Long,
    val noteText: String,
    val timeStamp: Long
)