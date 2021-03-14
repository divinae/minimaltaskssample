package com.dv.minimaltasksapp.di

import android.content.Context
import com.dv.minimaltasksapp.data.AppDatabase
import com.dv.minimaltasksapp.data.TasksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideTasksDao(appDatabase: AppDatabase): TasksDao {
        return appDatabase.tasksDao()
    }
}