package com.example.tasktimer.Model

import androidx.lifecycle.LiveData

class Repository(var noteDAO:TaskDao) {

        // ADD
    suspend fun insertTask(task:TaskTable){
        noteDAO.addNewTask(task)
    }// Done
        // Update
    suspend fun updateTask(task:TaskTable){
        noteDAO.updateTask(task)
    }// Done

    // Delete
    suspend fun deleteTask(task:TaskTable){
        noteDAO.deleteTask(task)
    }
     // Get all tasks as list
    fun getTasks():LiveData<List<TaskTable>>{
        return noteDAO.getAllTasks()
    }// Done
}