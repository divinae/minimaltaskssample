package com.dv.minimaltasksapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dv.minimaltasksapp.data.Task
import com.dv.minimaltasksapp.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksFragmentViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    fun getTasks(): LiveData<List<Task>> = taskRepository.getTasks()

    fun addTask(task: String) {
        viewModelScope.launch {
            taskRepository.addTask(task)
        }
    }

    fun removeTask(position: Int) {
        viewModelScope.launch {
            taskRepository.removeTask(position)
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            taskRepository.toggleTask(task)
        }
    }
}