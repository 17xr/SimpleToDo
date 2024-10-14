package com.example.simpletodo.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodo.model.container.Task
import com.example.simpletodo.model.container.TaskDetails
import com.example.simpletodo.model.container.isValid
import com.example.simpletodo.model.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isTaskValid: Boolean = false
)

class EditViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private var _editUiState: MutableStateFlow<EditUiState> = MutableStateFlow(EditUiState())

    val editUiState: StateFlow<EditUiState> = _editUiState.asStateFlow()

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.update(task = task)
        }
    }

    fun updateTaskDetails(taskDetails: TaskDetails) {
        _editUiState.update { currentState ->
            currentState.copy(
                taskDetails = taskDetails,
                isTaskValid = taskDetails.isValid()
            )
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.delete(task = task)
        }
    }
}
