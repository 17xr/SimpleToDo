package com.example.simpletodo.model.container

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.simpletodo.SimpleToDoApplication
import com.example.simpletodo.ui.edit.EditViewModel
import com.example.simpletodo.ui.entry.EntryViewModel
import com.example.simpletodo.ui.home.HomeViewModel
import com.example.simpletodo.ui.main.MainViewModel

object ViewModelFactory {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(taskRepository = simpleToDoApplication().container.taskRepository)
        }

        initializer {
            EntryViewModel(taskRepository = simpleToDoApplication().container.taskRepository)
        }

        initializer {
            EditViewModel(taskRepository = simpleToDoApplication().container.taskRepository)
        }
    }
}

fun CreationExtras.simpleToDoApplication(): SimpleToDoApplication =
    (this[APPLICATION_KEY] as SimpleToDoApplication)