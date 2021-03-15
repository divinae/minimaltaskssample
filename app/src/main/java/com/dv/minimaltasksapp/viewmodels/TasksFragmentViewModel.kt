package com.dv.minimaltasksapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dv.minimaltasksapp.data.Task
import com.dv.minimaltasksapp.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class TasksFragmentViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {

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
            Log.d("kuba_", "1")

            val result = suspendedToggleTask(task)

            Log.d("kuba_", "3 $result")
        }
    }

    private suspend fun suspendedToggleTask(task: Task) =
        suspendCoroutine<Boolean> { continuation ->
            viewModelScope.launch {
                taskRepository.toggleTask(task) {
                    Log.d("kuba_", "2")
                    continuation.resume(true)
                }
            }
        }
}