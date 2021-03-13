package com.dv.minimaltasksapp.viewmodels

import androidx.lifecycle.ViewModel
import com.dv.minimaltasksapp.data.models.Task
import com.dv.minimaltasksapp.repositories.TaskRepository
import com.dv.minimaltasksapp.repositories.TaskRepositoryImpl

class TaskItemViewModel(val task: Task) : ViewModel() {
    private val taskRepository: TaskRepository = TaskRepositoryImpl

    fun toggleTask(task: Task) {
        taskRepository.toggleTask(task)
    }
}