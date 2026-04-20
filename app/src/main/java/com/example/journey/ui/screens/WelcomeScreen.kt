package com.example.journey.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.journey.ui.theme.JourneyTheme

@Composable
fun Login(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onCreateAccountClick: () -> Unit = {},
    onGuestClick: () -> Unit = {}
) {

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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                //.fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(23.dp))
            Box(
                modifier = Modifier
                        .background(color = Color(0xFF927155), shape = RoundedCornerShape(12.dp))
                    .padding(14.dp),
            ) {
                Text(
                    text = "Welcome to Jou(R)ney!",
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxWidth(),
                    fontSize = 34.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                )
            }

            //Logic for Login button
            Spacer(modifier = Modifier.height(12.dp))
            ElevatedButton(
                onClick = onLoginClick,
                //border = BorderStroke(2.dp, Color(0xFF927155)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFF927155),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }

            //Logic for Create Account button
            Spacer(modifier = Modifier.height(12.dp))
            ElevatedButton(
                onClick = onCreateAccountClick,
                //border = BorderStroke(2.dp, Color(0xFF927155)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFF927155),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }

            //Continue as Guest button
            Spacer(modifier = Modifier.height(12.dp))
            ElevatedButton(
                onClick = onGuestClick,
                //border = BorderStroke(2.dp, Color(0xFF927155)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFF927155),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Continue as Guest",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
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
            Login()
        }
    }
}