package com.example.journey.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journey.data.local.dao.UserDao
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

class MainViewModel(private val userDao: UserDao): ViewModel() {

    private val _appState = MutableStateFlow<AppState>(AppState.Loading)
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    init{
        checkUserExists()
    }

    private fun checkUserExists(){
        viewModelScope.launch{
            val existingUser = userDao.getuser()

            if (existingUser == null){
                _appState.value = AppState.NeedsInformation
            } else {
                _appState.value = AppState.Ready
            }
        }
    }

    fun saveGuestUser(firstName: String, lastName: String){
        viewModelScope.launch {
            val newuser = User(
                firstName = firstName,
                lastName = lastName,
                role = "",
                joinDateTimeStamp = System.currentTimeMillis()
            )
            userDao.insertuser(newuser)
            _appState.value = AppState.Ready
        }
    }
}