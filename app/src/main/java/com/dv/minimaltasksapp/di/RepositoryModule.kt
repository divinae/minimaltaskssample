package com.dv.minimaltasksapp.di

import com.dv.minimaltasksapp.data.TasksDao
import com.dv.minimaltasksapp.repositories.TaskRepository
import com.dv.minimaltasksapp.repositories.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideTaskRepository(tasksDao: TasksDao): TaskRepository = TaskRepositoryImpl(tasksDao)
}