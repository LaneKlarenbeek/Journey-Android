package com.example.journey.ui.screens

import android.widget.Button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.journey.R
import com.example.journey.ui.theme.JourneyTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun HomePage(){
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
        Scaffold(
            // This is CRITICAL: It makes the Scaffold invisible so your gradient shows through
            containerColor = Color.Transparent,

            // 2. Define the Floating Action Button
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    // Set the background color of the button (optional, can be transparent if your image fills it)
                    containerColor = Color(0xFF927155),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_template_no_background_black),
                        contentDescription = "Create New Template",
                        modifier = Modifier.size(32.dp) // Adjust size as needed
                    )
                }
            }
        ) { innerPadding ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // This padding prevents content from hiding behind the button
                    .windowInsetsPadding(WindowInsets.statusBars),
                verticalArrangement = Arrangement.Center,
            ) {
                ComingSoon()
                Text(
                    text = "No Templates Yet",
                    color = Color.White
                )
            }
        }
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
            color = Color.Transparent,
        ) {
            HomePage()
        }
    }
}