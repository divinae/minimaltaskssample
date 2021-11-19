package com.dv.minimaltasksapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dv.minimaltasksapp.data.Task
import com.dv.minimaltasksapp.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksFragmentViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    fun getTasks(): Flow<List<Task>> = taskRepository.getTasks()

    fun addTask(task: String) {
        viewModelScope.launch {
            taskRepository.addTask(task)
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            taskRepository.removeTask(task)
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            taskRepository.toggleTask(task)
        }
    }
}