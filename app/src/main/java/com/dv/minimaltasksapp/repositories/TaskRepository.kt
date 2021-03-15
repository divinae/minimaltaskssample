package com.dv.minimaltasksapp.repositories

import androidx.lifecycle.LiveData
import com.dv.minimaltasksapp.data.Task

interface TaskRepository {
    fun getTasks(): LiveData<List<Task>>

    suspend fun addTask(taskText: String)

    suspend fun removeTask(position: Int)

    suspend fun toggleTask(task: Task, onSucces: () -> Unit)
}