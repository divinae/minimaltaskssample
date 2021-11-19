package com.dv.minimaltasksapp.repositories

import com.dv.minimaltasksapp.data.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun addTask(taskText: String)

    suspend fun removeTask(task: Task)

    suspend fun toggleTask(task: Task)
}