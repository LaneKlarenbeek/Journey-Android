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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
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
import com.example.journey.R
import com.example.journey.data.local.entity.JourneyRecord
import com.example.journey.data.local.entity.JourneyRecordWithDetails
import com.example.journey.data.local.entity.JourneyTemplate
import com.example.journey.data.local.entity.JourneyTemplateWithStops
import com.example.journey.data.local.entity.StopRecord
import com.example.journey.data.local.entity.StopRecordWithNotes
import com.example.journey.data.local.entity.StopTemplate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyStatusPage(
    activeJourney: JourneyRecordWithDetails,
    onCancelJourneyClick: () -> Unit = {},
    onNoteClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
){

    var currentStopIndex by remember { mutableStateOf(0) }

    var showCancelDialog by remember { mutableStateOf(false) }

    val journeyName = activeJourney.journey.title
    val stops = activeJourney?.stopsWithNotes?.sortedBy { it.stop.sequenceOrder } ?: emptyList()

    val currentStopName = stops.getOrNull(currentStopIndex)?.stop?.locationName ?: ""
    val nextStopName = stops.getOrNull(currentStopIndex + 1)?.stop?.locationName ?: "Finished"

    Box(
        modifier = Modifier
            .background(color = Color(0xFF927155), shape = RoundedCornerShape(12.dp))
            .padding(14.dp)
            .fillMaxSize(),
    ){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier
        ) {
            Spacer(modifier = Modifier.height(16.dp))

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
                                    color = Color.Black
                                )

                                /*TODO: Get the current stop name from the database*/
                                Text(
                                    text = currentStopName,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )

                            }
                        }
                    }
                }

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
                                    color = Color.Black
                                )
                                /*TODO: Get the next stop name from the database*/
                                Text(
                                    text = nextStopName,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            //Journey notes box shows the timestamps as well as any notes added to the journey
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .height(350.dp),
                shape = RoundedCornerShape(12.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    /*TODO: Create table sections (Sort of like a table) to store the notes & timestamps in*/

                }

            }

            Spacer(modifier = Modifier.weight(1f))

            //Row of buttons
            Row(){
                ElevatedButton(
                    modifier = Modifier,
                    onClick = { showCancelDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD0AE90))
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                ElevatedButton(
                    modifier = Modifier,
                    onClick = { /*TODO*/ },
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
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD0AE90))
                ) {
                    Text(
                        text = "Next",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
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
    }
}


@Preview
@Composable
fun JourneyStatusPagePreview(){

    val journeyName = "Rounds(HH/RV/CY)"

    val activeJourneyRecord = JourneyRecord(journeyId = 0, title = journeyName, startTimeStamp = 0)

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

    val testAcitveJourney = JourneyRecordWithDetails(journey = activeJourneyRecord, stopsWithNotes = stops)

    JourneyStatusPage(activeJourney = testAcitveJourney)
}