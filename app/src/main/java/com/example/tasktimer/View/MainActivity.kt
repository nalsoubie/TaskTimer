package com.example.tasktimer.View

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimer.ViewModel.MainViewModel
import com.example.tasktimer.ViewModel.TasksRV
import com.example.tasktimer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() , TasksRV.ClickListner {
    private lateinit var binding: ActivityMainBinding

    private lateinit var rvAdapter: TasksRV

    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAdapter= TasksRV(this)
        binding.mainRV.adapter=rvAdapter


        viewModel.getTasks().observe(this, {
                taskslist -> rvAdapter.update(taskslist)
        })






        binding.apply {


            bAdd.setOnClickListener{
                intentToAddTask()
            }
        }





    }//end create
    fun intentToAddTask(){
        var intent = Intent(this,AddTaskActivity::class.java)
        startActivity(intent)

    }




}// end main