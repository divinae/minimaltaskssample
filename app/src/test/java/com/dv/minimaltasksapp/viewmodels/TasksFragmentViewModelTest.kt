package com.dv.minimaltasksapp.viewmodels

import com.dv.minimaltasksapp.repositories.TaskRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ObsoleteCoroutinesApi
class TasksFragmentViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @RelaxedMockK
    lateinit var taskRepository: TaskRepository

    lateinit var tasksFragmentViewModel: TasksFragmentViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        tasksFragmentViewModel = TasksFragmentViewModel(taskRepository)
    }

    @Test
    fun checkThatGetTaskIsExecuted() {
        tasksFragmentViewModel.getTasks()

        verify { taskRepository.getTasks() }
    }

    @Test
    fun checkThatAddTaskIsExecuted() {
        tasksFragmentViewModel.addTask("xxx")

        coVerify { taskRepository.addTask("xxx") }
    }

    @After
    fun tearDown() {
        mainThreadSurrogate.close()
        unmockkAll()
    }
}