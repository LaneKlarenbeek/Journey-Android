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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HomePage(
    userName: String,
    onCreateTemplateClick: () -> Unit = {}
){

    var isMenuExpanded by remember { mutableStateOf(false) }

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
            containerColor = Color.Transparent,

            floatingActionButton = {
                // 2. A Column holds the expanding menu AND the main button
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // 3. The Animated Menu (Only visible when isMenuExpanded is true)
                    AnimatedVisibility(
                        visible = isMenuExpanded,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {

                            // Sub-Button: Create Template
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Optional Text Label next to the button
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFF927155),
                                    modifier = Modifier.padding(end = 4.dp)
                                ) {
                                    Text(
                                        text = "New Template",
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }

                                SmallFloatingActionButton(
                                    onClick = {
                                        isMenuExpanded = false // Close menu
                                        onCreateTemplateClick() // Route to new screen
                                    },
                                    containerColor = Color(0xFF927155),
                                    contentColor = Color.White
                                ) {
                                    // You can use a Text or an Icon here
                                    Text("+")
                                }
                            }

                            // You can copy/paste that Row above to add more sub-buttons here!
                        }
                    }

                    // 4. The Main Trigger Button at the bottom
                    FloatingActionButton(
                        onClick = {
                            // Toggles the state between true and false
                            isMenuExpanded = !isMenuExpanded
                        },
                        containerColor = Color(0xFF927155),
                        contentColor = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        // Bonus UX: Change the text/icon based on whether it is open!
                        if (isMenuExpanded) {
                            Text(
                                text = "X",
                                fontSize = 24.sp,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.add_template_no_background_white),
                                contentDescription = "Menu",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->



            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // This padding prevents content from hiding behind the button
                    .windowInsetsPadding(WindowInsets.statusBars),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hello",
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxWidth(),
                    fontSize = 34.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                )

                Text(
                    text = userName,
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxWidth(),
                    fontSize = 34.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                )

                Text(
                    text = "Ready to start a Journey?",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 4.dp)
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
            HomePage(
                userName = "User",
                onCreateTemplateClick = {}
            )
        }
    }
}