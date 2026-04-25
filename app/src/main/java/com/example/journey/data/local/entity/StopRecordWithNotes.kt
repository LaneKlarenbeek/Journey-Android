package com.example.journey.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class StopRecordWithNotes(
    @Embedded val stop: StopRecord,

    @Relation(
        parentColumn = "stopId",
        entityColumn = "stopOwnerId"
    )
    val notes: List<NoteRecord>
)