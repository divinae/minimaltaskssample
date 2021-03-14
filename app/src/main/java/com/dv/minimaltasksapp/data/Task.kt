package com.dv.minimaltasksapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    val text: String,
    var done: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
