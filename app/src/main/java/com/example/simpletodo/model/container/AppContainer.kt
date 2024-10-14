package com.example.simpletodo.model.container

import android.content.Context
import com.example.simpletodo.model.database.TaskDatabase
import com.example.simpletodo.model.repository.OfflineTaskRepository
import com.example.simpletodo.model.repository.TaskRepository

interface AppContainer {
    val taskRepository: TaskRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val taskRepository: TaskRepository by lazy {
        OfflineTaskRepository(taskDao = TaskDatabase.getDatabase(context = context).getTaskDao())
    }
}