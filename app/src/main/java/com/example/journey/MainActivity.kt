package com.example.journey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.journey.ui.screens.CreateAccount
import com.example.journey.ui.screens.Login
import com.example.journey.ui.screens.LoginScreen
import com.example.journey.ui.screens.TransitionPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JourneyTheme(darkTheme = false) {

                //Main controller for initial navigation through Startup menu's
                val navController = rememberNavController()

                //Primary Surface styled for background.
                NavHost(navController = navController, startDestination = "Login"){
                    composable("Login"){
                        Login(
                            onLoginClick = { navController.navigate("LoginScreen") },
                            onCreateAccountClick = { navController.navigate("CreateAccount") },
                            onGuestClick = { navController.navigate("HomePage") }
                        )
                    }

                    composable("LoginScreen"){
                        LoginScreen()
                    }

                    composable("CreateAccount"){
                        CreateAccount()
                    }

                    composable("HomePage"){
                        TransitionPage()
                    }

                }
            }
        }
    }
}