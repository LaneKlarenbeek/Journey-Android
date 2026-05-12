package com.example.journey.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.example.journey.data.local.entity.JourneyRecord
import com.example.journey.data.local.entity.JourneyRecordWithDetails
import com.example.journey.data.local.entity.StopRecord
import com.example.journey.data.local.entity.StopRecordWithNotes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import java.text.SimpleDateFormat
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.platform.LocalLocale

data class ListObject(
    val name: String,
    val stop: String,
    val stopId: Int,
    val timeStamp: Long
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyStatusPage(
    activeJourney: JourneyRecordWithDetails,
    onCancelJourneyClick: () -> Unit = {},
    addNoteAction: (stopId: Long, noteText: String) -> Unit,
    onNextClick: (StopRecord) -> Unit = {},
    onEndClick: () -> Unit = {},
){

    var currentStopIndex by remember { mutableIntStateOf(0) }
    var showCancelDialog by remember { mutableStateOf(false) }
    var showAddNoteDialog by remember { mutableStateOf(false)}

    var newNoteName by remember { mutableStateOf("") }

    val stops = activeJourney.stopsWithNotes.sortedBy { it.stop.sequenceOrder }


    val currentStopName = stops.getOrNull(currentStopIndex)?.stop?.locationName ?: "Finished"
    val nextStopName = stops.getOrNull(currentStopIndex + 1)?.stop?.locationName ?: ""

    val listObjects = remember(activeJourney) {
        val items = mutableListOf<ListObject>()

        items.add(
            ListObject(
                name = "Start",
                stop = "---",
                stopId = -1,
                timeStamp = activeJourney.journey.startTimeStamp
            )
        )

        activeJourney.stopsWithNotes.forEach { stopWithNotes ->
            stopWithNotes.stop.timeStamp?.let { ts ->
                items.add(ListObject(name = "Stop", stop = stopWithNotes.stop.locationName, stopId = currentStopIndex, timeStamp = ts))
            }

            stopWithNotes.notes.forEach { note ->
                items.add(ListObject(name = stopWithNotes.stop.locationName, stop = note.noteText, stopId = currentStopIndex, timeStamp = note.timeStamp))
            }
        }

        items.sortedBy { it.timeStamp }
    }

    Surface(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxSize(),
    ){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            //Header
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(color = Color.Transparent)
            ) {
                Text(
                    text = activeJourney.journey.title,
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxWidth()
                        .background(color = Color(0xFF927155)),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }

            /* ***************************
            * Below is the active area of the page
            * displaying all the information for
            * the current journey.
            * ****************************/

            //Current Stop side of the Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(200.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Surface(
                    modifier = Modifier
                        .width(150.dp)
                        .background(color = Color.Transparent)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = "Current",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline,
                                )
                                Text(
                                    text = currentStopName,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )

                            }
                        }
                    }
                }

                //Next Stop Side of the Row
                Surface(
                    modifier = Modifier
                        .width(150.dp)
                        .background(color = Color.Transparent)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = "Next",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline,
                                )
                                Text(
                                    text = nextStopName,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            //Journey Notes Section that shows Stops as well as Any Notes added.
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(12.dp),
                color = Color.Transparent,

            ){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    //Table that lists the timestamps of all stops and notes.
                    items(listObjects) { item ->
                        TimeTableListItem(
                            title = item.name,
                            stop = item.stop,
                            timeStamp = item.timeStamp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            //Row of buttons
            Row{
                ElevatedButton(
                    modifier = Modifier,
                    onClick = { showCancelDialog = true },
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                ElevatedButton(
                    modifier = Modifier,
                    onClick = {
                        showAddNoteDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD0AE90))
                ) {
                    Text(
                        text = "Note",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                ElevatedButton(
                    modifier = Modifier,
                    onClick = {

                        if(currentStopIndex < stops.size){
                            val currentStop = stops.getOrNull(currentStopIndex)?.stop
                            if (currentStop != null) {
                                onNextClick(currentStop)

                                if (currentStopIndex < stops.size) {
                                    currentStopIndex++
                                }
                            }
                        } else if(currentStopIndex == stops.size){
                            onEndClick()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD0AE90))
                ) {
                    if(currentStopIndex < stops.size){
                        Text(
                            text = "Next",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    } else if(currentStopIndex == stops.size){
                        Text(
                            text = "End",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        if (showCancelDialog) {
            AlertDialog(
                onDismissRequest = {
                    // Closes the dialog if they tap outside of it
                    showCancelDialog = false
                },
                title = {
                    Text(
                        text = "Cancel Journey?",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF896A4E)
                    )
                },
                text = {
                    Text(
                        "Are you sure you want to cancel? All timestamps and notes collected during this journey will be permanently deleted."
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showCancelDialog = false
                            // THIS is where we finally tell MainActivity to wipe the data!
                            onCancelJourneyClick()
                        }
                    ) {
                        Text("Yes, Cancel", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            // Just closes the window and lets them continue the round
                            showCancelDialog = false
                        }
                    ) {
                        Text("Resume", color = Color.Gray)
                    }
                },
                containerColor = Color.White
            )
        }

        if(showAddNoteDialog){
            Dialog(
                onDismissRequest = { showAddNoteDialog = false }
            ){
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Add Note",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF896A4E)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = newNoteName,
                            onValueChange = {newNoteName = it},
                            placeholder = { Text("e.g., Spotted mess on floor") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF927155),
                                focusedLabelColor = Color(0xFF927155)
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(
                                onClick = { showAddNoteDialog = false },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text("Cancel", color = Color.Gray)
                            }

                            Button(
                                onClick = {
                                    val currentStopId = stops.getOrNull(currentStopIndex)?.stop?.stopId

                                    if(currentStopId != null && newNoteName.isNotBlank()){

                                        addNoteAction(currentStopId, newNoteName)

                                        newNoteName = ""
                                        showAddNoteDialog = false
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF927155
                                    )
                                )
                            ) {
                                Text("Continue")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun JourneyStatusPagePreview(){

    val journeyName = "Rounds(HH/RV/CY)"

    val activeJourneyRecord = JourneyRecord(journeyId = 0, title = journeyName, startTimeStamp = 0, endTimeStamp = 0)

    val stops: List<StopRecordWithNotes> = listOf(
        StopRecordWithNotes(
            stop = StopRecord(stopId = 0, journeyOwnerId = 0, locationName = "Higbie Hall", sequenceOrder = 0),
            notes = emptyList()
        ),
        StopRecordWithNotes(
            stop = StopRecord(stopId = 1, journeyOwnerId = 0, locationName = "Residence Village", sequenceOrder = 1),
            notes = emptyList()
        ),
        StopRecordWithNotes(
            stop = StopRecord(stopId = 2, journeyOwnerId = 0, locationName = "Courtyard Hall", sequenceOrder = 2),
            notes = emptyList()
        )
    )

    val testActiveJourney = JourneyRecordWithDetails(journey = activeJourneyRecord, stopsWithNotes = stops)

    //JourneyStatusPage(activeJourney = testActiveJourney)
}

@Composable
fun TimeTableListItem(
    title: String,
    stop: String,
    timeStamp: Long
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = stop,
                modifier = Modifier.weight(1.5f),
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            val formatter = SimpleDateFormat("HH:mm", LocalLocale.current.platformLocale)
            val formattedTime: String = formatter.format(java.util.Date(timeStamp))

            Text(
                text = formattedTime,
                modifier = Modifier.weight(0.7f),
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                color = Color.Gray
            )
        }
    }
}