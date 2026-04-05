package com.example.journey

import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.journey.ui.theme.JourneyTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import android.view.View

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val rootLayout: View = findViewById(android.R.id.content)
        rootLayout.setBackgroundResource(R.drawable.tan_gradient_background)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JourneyTheme {
                Login()
            }
        }
    }
}

@Composable
fun Login(modifier: Modifier = Modifier) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.statusBars)) {

        Spacer(modifier = Modifier.height(23.dp))
        Box(
            modifier = Modifier
                .background(color = Color(0xFF927155), shape = RoundedCornerShape(12.dp))
                .padding(14.dp)
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
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .background(color = Color(0xFF927155), shape = RoundedCornerShape(12.dp))
                .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Create Account",
                modifier = Modifier.padding(8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .background(color = Color(0xFF927155), shape = RoundedCornerShape(12.dp))
                .padding(top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Login",
                modifier = Modifier.padding(8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    Login()
}