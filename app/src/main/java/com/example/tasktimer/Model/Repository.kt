package com.example.tasktimer.Model

import androidx.lifecycle.LiveData

class Repository(var taskDao:TaskDao) {

        // ADD
    suspend fun insertTask(task:TaskTable){
        taskDao.addNewTask(task)
    }// Done


        // Update
    suspend fun updateTask(task:TaskTable){
        taskDao.updateTask(task)
    }// Done

    // Delete
    suspend fun deleteTask(task:TaskTable){
        taskDao.deleteTask(task)
    }

     // Get all tasks as list
     fun getTasks():LiveData<List<TaskTable>>{
        return taskDao.getAllTasks()
    }// Done
    fun getTasksByID():LiveData<List<TaskTable>>{
        return taskDao.getAllTasksByID()
    }// Done
    fun getTasksByAlpha():LiveData<List<TaskTable>>{
        return taskDao.getAllTasksbyAlpa()
    }// Done

    fun getTasksBloean():List<TaskTable>{
        return taskDao.getAllTasksBoolean()
    }// Done

}