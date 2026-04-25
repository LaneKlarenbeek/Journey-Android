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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun JourneyStatusPage(
    modifier: Modifier = Modifier,
    journeyName: String,
    onEndClick: () -> Unit = {},
){
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
                    text = journeyName,
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxWidth()
                        .background(color = Color(0xFF927155)),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }

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
                    onClick = { onEndClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD0AE90))
                ) {
                    Text(
                        text = "End",
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

    }
}


@Preview
@Composable
fun JourneyStatusPagePreview(){
    JourneyStatusPage(modifier = Modifier, journeyName = "Journey Name")
}