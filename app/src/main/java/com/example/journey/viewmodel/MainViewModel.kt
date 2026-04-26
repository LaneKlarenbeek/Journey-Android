package com.example.journey.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journey.data.local.dao.JourneyRecordDao
import com.example.journey.data.local.dao.JourneyTemplateDao
import com.example.journey.data.local.dao.NoteDao
import com.example.journey.data.local.dao.UserDao
import com.example.journey.data.local.entity.JourneyRecord
import com.example.journey.data.local.entity.JourneyTemplate
import com.example.journey.data.local.entity.JourneyTemplateWithStops
import com.example.journey.data.local.entity.NoteRecord
import com.example.journey.data.local.entity.StopRecord
import com.example.journey.data.local.entity.StopTemplate
import com.example.journey.data.local.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

sealed class AppState {
    object Loading : AppState()
    object NeedsInformation: AppState()
    object Ready: AppState()
}

class MainViewModel(
    private val userDao: UserDao,
    private val journeyTemplateDao: JourneyTemplateDao,
    private val journeyRecordDao: JourneyRecordDao,
    private val noteDao: NoteDao
): ViewModel() {

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

        Log.d("DatabaseTest", "Button Clicked! Name: '$templateName', Stops: ${stopNames.size}")
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

    //Creating the flow interface for the database to be interacted with dynamically.
    val templates: StateFlow<List<JourneyTemplateWithStops>> = journeyTemplateDao.getJourneyTemplatesWithStops()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteTemplate(template: JourneyTemplate){
        viewModelScope.launch{
            try{
                journeyTemplateDao.deleteJourneyTemplate(template)
                Log.d("DatabaseTest", "SUCCESS! Template '${template.title}' deleted.")
            } catch (e: Exception){
                Log.e("DatabaseTest", "Failed to delete template: ${e.message}", e)
            }
        }
    }

    fun UpdateTemplate(templateId: Long, newName: String, newStops: List<String>){
        viewModelScope.launch{
            try{
                val updatedTemplate = JourneyTemplate(templateId, newName)
                journeyTemplateDao.updateJourneyTemplate(updatedTemplate)

                journeyTemplateDao.deleteStopsForTemplateById(templateId)

                val newStopTemplates = newStops.mapIndexed { index, name ->
                    StopTemplate(
                        templateOwnerId = templateId,
                        locationName = name,
                        sequenceOrder = index
                    )
                }

                journeyTemplateDao.insertStopTemplate(newStopTemplates)
                Log.d("DatabaseTest", "SUCCESS! Template '$newName' updated.")

            } catch(e: Exception){
                Log.e("DatabaseTest", "Failed to update template: ${e.message}", e)
            }
        }
    }

    fun startNewJourney(template: JourneyTemplateWithStops, onJourneyStarted: (Long) -> Unit){
        viewModelScope.launch {
            try{
                val newRecord = JourneyRecord(
                    title = template.template.title,
                    startTimeStamp = System.currentTimeMillis()
                )
                val newJourneyId = journeyRecordDao.insertJourneyRecord(newRecord)

                val stopRecords = template.stops.map { stopTemplate ->
                    StopRecord(
                        journeyOwnerId = newJourneyId,
                        locationName = stopTemplate.locationName,
                        sequenceOrder = stopTemplate.sequenceOrder
                    )
                }

                journeyRecordDao.insertStopRecords(stopRecords)

                onJourneyStarted(newJourneyId)
            } catch (e: Exception){
                Log.e("DatabaseTest", "Failed to start new journey: ${e.message}", e)
            }
        }
    }
    fun getActiveJourneyFlow(journeyId: Long) = journeyRecordDao.getActiveJourneyById(journeyId)

    fun cancelActiveJourney(journeyId: Long){
        viewModelScope.launch{
            try {
                val recordToDelete =
                    JourneyRecord(journeyId = journeyId, title = "", startTimeStamp = 0)

                journeyRecordDao.deleteJourneyRecord(recordToDelete)


                Log.d("DatabaseTest", "SUCCESS! Journey $journeyId cancelled.")

            } catch (e: Exception) {
                Log.e("DatabaseTest", "Failed to cancel journey: ${e.message}", e)
            }
        }
    }

    fun markStopCompleted(stop: StopRecord) {
        viewModelScope.launch {
            try {
                val completedStop = stop.copy(timeStamp = System.currentTimeMillis())

                journeyRecordDao.updateStopRecord(completedStop)

                Log.d("DatabaseTest", "SUCCESS! ${stop.locationName} stamped at ${completedStop.timeStamp}.")
            } catch (e: Exception) {
                Log.e("DatabaseTest", "Failed to mark stop complete: ${e.message}", e)
            }
        }
    }

    fun addNewJourneyNote(stopId: Long, text: String){
        viewModelScope.launch{
            try{
                val newNote = NoteRecord(
                    stopOwnerId = stopId,
                    noteText = text,
                    timeStamp = System.currentTimeMillis()
                )
                noteDao.insertNote(newNote)
                Log.d("DatabaseTest", "SUCCESS! Note added to Stop $stopId")

            } catch (e: Exception) {
                Log.e("DatabaseTest", "Failed to add note: ${e.message}", e)
            }
        }
    }
}