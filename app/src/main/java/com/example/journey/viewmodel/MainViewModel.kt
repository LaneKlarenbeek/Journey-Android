package com.example.journey.viewmodel

import android.util.Log
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

        Log.d("DatabaseTest", "ViewModel recieved: $firstName $lastName")
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
}