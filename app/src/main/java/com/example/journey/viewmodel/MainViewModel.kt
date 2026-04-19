package com.example.journey.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journey.data.local.dao.JourneyTemplateDao
import com.example.journey.data.local.dao.UserDao
import com.example.journey.data.local.entity.JourneyTemplate
import com.example.journey.data.local.entity.StopTemplate
import com.example.journey.data.local.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AppState {
    object Loading : AppState()
    object NeedsInformation: AppState()
    object Ready: AppState()
}

class MainViewModel(private val userDao: UserDao, private val journeyTemplateDao: JourneyTemplateDao): ViewModel() {

    private val _appState = MutableStateFlow<AppState>(AppState.Loading)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init{
        checkUserExists()
    }

    private fun checkUserExists(){
        viewModelScope.launch{
            val existingUser = userDao.getUser()

            if (existingUser == null){
                _appState.value = AppState.NeedsInformation
            } else {
                _currentUser.value = existingUser
                _appState.value = AppState.Ready
            }
        }
    }

    fun saveGuestUser(firstName: String, lastName: String){

        Log.d("DatabaseTest", "ViewModel received: $firstName $lastName")
        viewModelScope.launch {
            try {
                val newUser = User(
                    firstName = firstName,
                    lastName = lastName,
                    role = "",
                    joinDateTimeStamp = System.currentTimeMillis()
                )
                userDao.insertUser(newUser)

                Log.d("DatabaseTest", "ViewModel saved: $firstName $lastName")

                _currentUser.value = newUser
                _appState.value = AppState.Ready

            } catch(e: Exception){
                Log.e("DatabaseTest", "ViewModel failed to save: $firstName $lastName")

            }
        }
    }

    fun saveTemplate(templateName: String, stopNames: List<String>) {
        viewModelScope.launch {
            try {
                // 1. Wrap the String inside your Database Entity object!
                val newTemplate = JourneyTemplate(title = templateName)

                // 2. Pass the OBJECT to the DAO, which returns your new ID
                val newTemplateId = journeyTemplateDao.insertJourneyTemplate(newTemplate)

                // 3. Map the stops, attaching the new ID so they link together
                val stopTemplates = stopNames.mapIndexed { index, name ->
                    StopTemplate(
                        templateOwnerId = newTemplateId,
                        locationName = name,
                        sequenceOrder = index
                    )
                }

                // 4. Save the stops to the database
                // (Make sure this matches the exact name in your DAO!)
                journeyTemplateDao.insertStopTemplate(stopTemplates)

                Log.d("DatabaseTest", "SUCCESS! Template '$templateName' saved for rounds.")

            } catch (e: Exception) {
                Log.e("DatabaseTest", "Failed to save template: ${e.message}", e)
            }
        }
    }
}