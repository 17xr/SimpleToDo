package com.example.simpletodo.model.repository

import com.example.simpletodo.model.container.Task
import com.example.simpletodo.model.database.TaskDao
import kotlinx.coroutines.flow.Flow

class OfflineTaskRepository(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun insert(task: Task) = taskDao.insert(task = task)

    override suspend fun update(task: Task) = taskDao.update(task = task)

    override suspend fun delete(task: Task) = taskDao.delete(task = task)

    override fun getTask(id: Int): Flow<Task> = taskDao.getTask(id = id)

    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
}