package com.example.tasktimer.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimer.R
import com.example.tasktimer.ViewModel.AddTaskViewModel
import com.example.tasktimer.ViewModel.MainViewModel
import com.example.tasktimer.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding


    private val viewModel by lazy{ ViewModelProvider(this).get(AddTaskViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }//end create



}// end main