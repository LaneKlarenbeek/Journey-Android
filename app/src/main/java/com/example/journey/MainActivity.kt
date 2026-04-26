package com.example.journey

import android.os.Bundle
import android.transition.Transition
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.journey.ui.theme.JourneyTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.journey.ui.screens.CreateAccount
import com.example.journey.ui.screens.Login
import com.example.journey.ui.screens.LoginScreen
import com.example.journey.ui.screens.TransitionPage
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.journey.viewmodel.MainViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.journey.data.local.entity.JourneyRecord
import com.example.journey.data.local.entity.JourneyRecordWithDetails
import com.example.journey.data.local.entity.JourneyTemplate
import com.example.journey.data.local.entity.JourneyTemplateWithStops
import com.example.journey.data.local.entity.NoteRecord
import com.example.journey.data.local.entity.StopTemplate
import com.example.journey.ui.screens.HomePage
import com.example.journey.ui.screens.JourneyStatusPage
import com.example.journey.viewmodel.AppState

//import com.example.journey.data.local.entity.Journey
//import com.example.journey.data.repository.JourneyRepository
//import com.example.journey.viewmodel.JourneyViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels{
        object: ViewModelProvider.Factory{
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val database = androidx.room.Room.databaseBuilder(
                    applicationContext,
                    com.example.journey.data.local.AppDatabase::class.java,
                    "journey_database"
                ).build()

                if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(
                        database.userDao(),
                        database.journeyTemplateDao(),
                        database.journeyRecordDao(),
                        database.noteDao()
                    ) as T
                }

                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    database.userDao(),
                    database.journeyTemplateDao(),
                    database.journeyRecordDao(),
                    database.noteDao()
                ) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JourneyTheme(darkTheme = false) {

                // 1. This is the magic line. It continuously watches the ViewModel for changes.
                val appState by mainViewModel.appState.collectAsState()

                // 2. The Traffic Director
                when (appState) {

                    is AppState.Loading -> {
                        // Keep blank while checking the database on startup
                    }

                    is AppState.NeedsInformation -> {
                        // The user is not in the database. Show the Onboarding Flow.
                        val navController = rememberNavController()

                        NavHost(navController = navController, startDestination = "Login") {
                            composable("Login"){
                                Login(
                                    onLoginClick = { navController.navigate("LoginScreen") },
                                    onCreateAccountClick = { navController.navigate("CreateAccount") },
                                    onGuestClick = { navController.navigate("TransitionScreen") }
                                )
                            }

                            composable("LoginScreen"){
                                LoginScreen()
                            }

                            composable("CreateAccount"){
                                CreateAccount()
                            }

                            // Renamed from "HomePage" to "TransitionScreen" to avoid confusion
                            composable("TransitionScreen"){
                                TransitionPage(
                                    onContinueClick = { enteredFirstName, enteredLastName ->
                                        // 3. This calls the ViewModel.
                                        // The ViewModel saves the DB, then flips the appState to "Ready".
                                        mainViewModel.saveGuestUser(enteredFirstName, enteredLastName)
                                    }
                                )
                            }
                        }
                    }

                    is AppState.Ready -> {
                        val user by mainViewModel.currentUser.collectAsState()
                        val firstName = user?.firstName ?: "RA"

                        // Grab the live list of templates!
                        val templates by mainViewModel.templates.collectAsState()

                        val mainNavController = rememberNavController()


                        NavHost(navController = mainNavController, startDestination = "HomePage"){
                            composable("HomePage"){
                                HomePage(
                                    userName = firstName,
                                    templates = templates,
                                    onCreateTemplateClick = { },
                                    onSaveTemplate = { name, stops ->
                                        mainViewModel.saveTemplate(name, stops)
                                    },
                                    onDeleteTemplate = { template ->
                                        mainViewModel.deleteTemplate(template)
                                    },
                                    onEditTemplate = { templateId, newName, newStops ->
                                        mainViewModel.UpdateTemplate(templateId, newName, newStops)
                                    },
                                    onJourneyStart = { templateData ->
                                        mainViewModel.startNewJourney(templateData) { newRecordId ->
                                            mainNavController.navigate("JourneyStatus/$newRecordId")
                                        }
                                    }
                                )
                            }
                            composable(
                                route = "JourneyStatus/{journeyId}",
                                arguments = listOf(navArgument("journeyId") { type = NavType.LongType })
                            ){ backStackEntry ->
                                val journeyId = backStackEntry.arguments?.getLong("journeyId") ?:0

                                val activeJourney by mainViewModel.getActiveJourneyFlow(journeyId).collectAsState(null)

                                if (activeJourney != null) {
                                    JourneyStatusPage(
                                        activeJourney = activeJourney!!, /*TODO: Replace the !! operator with a safer alternative*/
                                        onCancelJourneyClick = {
                                            mainNavController.popBackStack()
                                            mainViewModel.cancelActiveJourney(journeyId)
                                        },
                                        addNoteAction = { stopId, text ->
                                            mainViewModel.addNewJourneyNote(stopId, text)
                                        },
                                        onNextClick = { stopRecord ->
                                            mainViewModel.markStopCompleted(stopRecord)
                                        },
                                        onEndClick = {},
                                    )
                                } else {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}