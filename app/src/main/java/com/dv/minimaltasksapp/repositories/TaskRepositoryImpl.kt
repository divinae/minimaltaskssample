package com.dv.minimaltasksapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dv.minimaltasksapp.data.models.Task

object TaskRepositoryImpl : TaskRepository {
    private val tasks = MutableLiveData(listOf(Task("get A"), Task("get B"), Task("do x")))

    override fun getTasks(): LiveData<List<Task>> = tasks

    override fun addTask(taskText: String) {
        tasks.value = tasks.value?.toMutableList()?.apply {
            add(Task(taskText))
        }
    }

    override fun removeTask(position: Int) {
        val newList = tasks.value?.toMutableList()?.apply {
            removeAt(position)
        }

        tasks.postValue(newList)
    }

    override fun toggleTask(task: Task) {
        val newList = tasks.value?.map {
            if (it.text == task.text) {
                Task(it.text, !it.done)
            } else {
                it
            }
        }

        tasks.postValue(newList)
    }
}