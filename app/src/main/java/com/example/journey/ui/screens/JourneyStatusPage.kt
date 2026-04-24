package com.example.journey.ui.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.computeHorizontalBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        ){
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

            //Journey status box shows the current stop and the next stop
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .height(250.dp),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    text = "Journey Status Box",
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            //Journey notes box shows the timestamps as well as any notes added to the journey
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .height(350.dp),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    text = "Journey Notes & Times Box",
                )
            }

            Spacer(modifier = Modifier.weight(1f))

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