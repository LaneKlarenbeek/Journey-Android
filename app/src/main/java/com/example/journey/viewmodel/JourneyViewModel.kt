package com.example.journey.viewmodel
/*

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journey.data.repository.JourneyRepository
import com.example.journey.data.local.entity.Journey
import com.example.journey.data.local.entity.JourneyWithStops
import com.example.journey.data.local.entity.Stop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JourneyViewModel(private val journeyRepository: JourneyRepository) : ViewModel(){

    private val _journeys = MutableStateFlow<List<JourneyWithStops>>(emptyList())
    val journeysList: StateFlow<List<JourneyWithStops>> = _journeys.asStateFlow()
    
    init {
        loadAllJourneys()
    }

    private fun loadAllJourneys(){
        viewModelScope.launch {
            _journeys.value = journeyRepository.getAllJourneys()
        }
    }

    fun saveNewJourney(title: String, locations: List<String>){
        viewModelScope.launch {
            val newJourney = Journey(title = title, startDateTimeStamp = System.currentTimeMillis())

            val stops = locations.mapIndexed { index, location ->
                Stop(journeyOwnerId = 0, locationName = location, sequenceOrder = index)
            }

            journeyRepository.addJourneyWithStops(newJourney, stops)

            loadAllJourneys()
        }
    }
}
*/