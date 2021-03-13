package com.dv.minimaltasksapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dv.minimaltasksapp.data.models.Task
import com.dv.minimaltasksapp.repositories.TaskRepository
import com.dv.minimaltasksapp.repositories.TaskRepositoryImpl

class TaskFragmentViewModel : ViewModel() {
    private val taskRepository: TaskRepository = TaskRepositoryImpl

    fun getTasks(): LiveData<List<Task>> = taskRepository.getTasks()

    fun addTask(task: String) {
        taskRepository.addTask(task)
    }
}