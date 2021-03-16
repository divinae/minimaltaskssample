package com.dv.minimaltasksapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dv.minimaltasksapp.repositories.TaskRepository
import java.lang.IllegalArgumentException

class TaskFragmentViewModelFactory(
    private val taskRepository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksFragmentViewModel::class.java)) {
            return TasksFragmentViewModel(taskRepository) as T
        } else {
            throw IllegalArgumentException("View model not found")
        }
    }
}