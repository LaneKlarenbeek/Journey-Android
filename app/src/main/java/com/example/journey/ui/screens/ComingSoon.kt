package com.example.journey.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.journey.R

@Composable
fun ComingSoon(){
    Box(
        modifier = Modifier
            .background(color = Color(0xFF927155), shape = RoundedCornerShape(12.dp))
            .padding(14.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.location_on),
                contentDescription = "Location Icon",
                modifier = Modifier
                    .size(50.dp),
            )
            Text(
                text = "Coming Soon",
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxWidth(),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 36.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComingSoonPreview() {
    ComingSoon()
}