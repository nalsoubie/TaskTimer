package com.example.tasktimer.Model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM Tasks ORDER BY priority")
    fun getAllTasks(): LiveData<List<TaskTable>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewTask(task: TaskTable)

    @Delete
    fun deleteTask(task: TaskTable)

    @Update
    fun updateTask(task: TaskTable)

//    @Update
//    fun updateTaskStatus(status: Boolean, givenPk: Int)
//
//
//
//    @Query("UPDATE Tasks SET taskTime = :taskTime  WHERE pk = :givenPk")
//    fun updateTaskTime(taskTime: Long, givenPk: Int)

   // @Query("UPDATE Tasks SET isRunning = :status WHERE pk = :givenPk")




//    @Query("SELECT * FROM Tasks WHERE pk = :givenPk")
//    fun getTask(givenPk: Int): TaskTable
//
//    @Query("UPDATE Tasks SET taskTime = :taskTime WHERE pk = :givenPk")
//    fun updateTaskTime(taskTime: Long, givenPk: Int)
//
//    @Query("UPDATE Tasks SET isRunning = :status WHERE pk = :givenPk")
//    fun updateTaskStatus(status: Boolean, givenPk: Int)
} //end interface