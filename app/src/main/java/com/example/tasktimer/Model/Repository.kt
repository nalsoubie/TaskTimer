package com.example.tasktimer.Model

import androidx.lifecycle.LiveData


class Repository (private val taskDao: TaskDao) {

    val getTasks: LiveData<List<TaskTable>> = taskDao.getAllTasks()




}