package com.example.simpletodo.ui.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodo.model.container.TaskDetails
import com.example.simpletodo.model.container.isValid
import com.example.simpletodo.model.container.toTask
import com.example.simpletodo.model.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EntryUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isTaskValid: Boolean = false
)

class EntryViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private var _entryUiState: MutableStateFlow<EntryUiState> = MutableStateFlow(EntryUiState())

    val entryUiState: StateFlow<EntryUiState> = _entryUiState.asStateFlow()

    fun insertTask(taskDetails: TaskDetails) {
        viewModelScope.launch {
            if (taskDetails.isValid()) {
                taskRepository.insert(task = taskDetails.toTask())
            }
        }
    }

    fun updateTaskDetails(taskDetails: TaskDetails) {
        _entryUiState.update { currentState ->
            currentState.copy(
                taskDetails = taskDetails,
                isTaskValid = taskDetails.isValid()
            )
        }
    }

    fun clearUiState() {
        _entryUiState.update { EntryUiState() }
    }
}
