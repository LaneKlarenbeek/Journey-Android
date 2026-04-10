package com.example.journey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.journey.ui.theme.JourneyTheme

@Composable
fun HomePage(){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        //.fillMaxSize()
        .windowInsetsPadding(WindowInsets.statusBars),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "This is the Home Page"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview(){
    JourneyTheme(darkTheme = false, dynamicColor = false) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFD0AE90),
                            Color(0xFF896A4E)
                        ),
                        startY = 0.0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                ),
            color = Color.Transparent
        ) {
            HomePage()
        }
    }
}