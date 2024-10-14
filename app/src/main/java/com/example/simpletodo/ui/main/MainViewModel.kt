package com.example.simpletodo.ui.main

import androidx.lifecycle.ViewModel
import com.example.simpletodo.model.container.AppScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MainUiState(
    val appScreen: AppScreen = AppScreen.Home
)

class MainViewModel : ViewModel() {
    private var _appUiState = MutableStateFlow(
        MainUiState()
    )

    val appUiState: StateFlow<MainUiState> = _appUiState.asStateFlow()

    fun switchScreen(appScreen: AppScreen) {
        _appUiState.update { currentState ->
            currentState.copy(appScreen = appScreen)
        }
    }
}