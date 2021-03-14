package com.dv.minimaltasksapp.repositories

import androidx.lifecycle.LiveData
import com.dv.minimaltasksapp.data.Task
import com.dv.minimaltasksapp.data.TasksDao
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val tasksDao: TasksDao) : TaskRepository {

    private val tasks = tasksDao.getTasks()

    override fun getTasks(): LiveData<List<Task>> = tasks

    override suspend fun addTask(taskText: String) {
        tasksDao.insert(Task(taskText))
    }

    override suspend fun removeTask(position: Int) {
        tasks.value?.get(position)?.also {
            tasksDao.delete(it)
        }
    }

    override suspend fun toggleTask(task: Task) {
        tasksDao.update(task.copy(done = !task.done))
    }
}