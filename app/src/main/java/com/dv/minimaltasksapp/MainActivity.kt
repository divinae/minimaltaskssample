package com.dv.minimaltasksapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// TODO write javaDoc in all public methods
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            // TODO use navigator
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TasksFragment.newInstance())
                .commitNow()
        }
    }
}