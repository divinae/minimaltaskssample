package com.dv.minimaltasksapp.repositories

import com.dv.minimaltasksapp.data.Task
import com.dv.minimaltasksapp.data.TasksDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val tasksDao: TasksDao) : TaskRepository {

    private val tasks = tasksDao.getTasks()

    override fun getTasks(): Flow<List<Task>> = tasks

    override suspend fun addTask(taskText: String) {
        tasksDao.insert(Task(taskText))
    }

    override suspend fun removeTask(task: Task) {
        tasksDao.delete(task)

    }

    override suspend fun toggleTask(task: Task) {
        tasksDao.update(task.copy(done = !task.done))
    }
}