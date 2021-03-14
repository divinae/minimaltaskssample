package com.dv.minimaltasksapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO write javaDoc in all public methods
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var tasksFragment: TasksFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            // TODO use navigator
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, tasksFragment)
                .commitNow()
        }
    }
}