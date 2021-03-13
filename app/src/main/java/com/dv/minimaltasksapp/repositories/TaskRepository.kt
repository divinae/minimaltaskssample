package com.dv.minimaltasksapp.repositories

import androidx.lifecycle.LiveData
import com.dv.minimaltasksapp.data.models.Task

interface TaskRepository {
    fun getTasks(): LiveData<List<Task>>

    fun addTask(taskText: String)

    fun removeTask(position: Int)

    fun toggleTask(task: Task)
}