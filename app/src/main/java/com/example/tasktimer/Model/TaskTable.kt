package com.example.tasktimer.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class TaskTable(
    @PrimaryKey(autoGenerate = true)
    val pk: Int, //
    val taskName: String, //
    val taskDescription: String, //
    var taskTime: Long, //
    val priority: String, //
    var isRunning: Boolean = false //
)
