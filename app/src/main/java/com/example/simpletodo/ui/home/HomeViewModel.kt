package com.example.simpletodo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodo.model.container.Task
import com.example.simpletodo.model.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val taskList: List<Task> = listOf()
)

class HomeViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private var _homeUiState = MutableStateFlow(
        HomeUiState()
    )

    val homeUiState = _homeUiState.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository
                .getAllTasks()
                .collect {
                    _homeUiState.update { currentState ->
                        currentState.copy(taskList = it)
                    }
                }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.update(task = task)
        }
    }
}