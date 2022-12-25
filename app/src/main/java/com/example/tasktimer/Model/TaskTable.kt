package com.example.tasktimer.Model

import android.view.MenuItem
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class TaskTable(
    @PrimaryKey(autoGenerate = true)
    val pk: Int, //
    var taskName: String, //
    var taskDescription: String, //
    var taskTime: Long, //
    val priority: String, //
    var isRunning: Boolean = false, //
)
