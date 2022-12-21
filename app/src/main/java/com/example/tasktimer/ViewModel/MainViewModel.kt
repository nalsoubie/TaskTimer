package com.example.tasktimer.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tasktimer.Model.Repository
import com.example.tasktimer.Model.TaskDatabase
import com.example.tasktimer.Model.TaskTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (application: Application): AndroidViewModel(application) {


    private val repository: Repository
    private val allTasks: LiveData<List<TaskTable>>

    init {
        val taskDao = TaskDatabase.getInstance(application).taskDao()
        repository = Repository(taskDao)
        allTasks = repository.getTasks()
    }//end init
    fun addNote(task: TaskTable){
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertTask(task)
        }
    }
    fun updateNote(task: TaskTable){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateTask(task)
        }

    }
    fun deleteNote(task: TaskTable){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteTask(task)
        }

    }
    fun getNotes():LiveData<List<TaskTable>>{
        return repository.getTasks()
    }




}// end VM