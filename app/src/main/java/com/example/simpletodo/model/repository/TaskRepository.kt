package com.example.simpletodo.model.repository

import com.example.simpletodo.model.container.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insert(task: Task)

    suspend fun update(task: Task)

    suspend fun delete(task: Task)

    fun getTask(id: Int): Flow<Task>

    fun getAllTasks(): Flow<List<Task>>
}