package com.example.journey.utils

import java.text.SimpleDateFormat
import com.example.journey.data.local.entity.JourneyRecordWithDetails
import java.util.Date


fun formatFullJourney(
    details: JourneyRecordWithDetails,
    timeFormatter: SimpleDateFormat
): String = buildString {

    // 1. Journey Header
    val startTime = timeFormatter.format(Date(details.journey.startTimeStamp))
    appendLine("$startTime - ${details.journey.title}")
    appendLine()

    // 2. Iterate through nested Stops
    details.stopsWithNotes.forEach { stopWithNotes ->
        val stopTime = timeFormatter.format(Date(stopWithNotes.stop.timeStamp ?: details.journey.startTimeStamp))
        appendLine("$stopTime - ${stopWithNotes.stop.locationName}")

        // 3. Nested Notes for this specific Stop
        stopWithNotes.notes.forEach { note ->
            val noteTime = timeFormatter.format(Date(note.timeStamp))
            appendLine("$noteTime - ${note.noteText}")
        }
        appendLine()
    }

    // 4. Journey Footer
    // Using your requirement: end time - "--"
    val endTime = details.journey.endTimeStamp?.let {
        timeFormatter.format(Date(it))
    } ?: "--"

    appendLine("$endTime - --")
}