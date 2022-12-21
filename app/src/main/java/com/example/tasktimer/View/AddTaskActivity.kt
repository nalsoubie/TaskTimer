package com.example.tasktimer.View

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimer.ViewModel.MainViewModel
import com.example.tasktimer.databinding.ActivityAddTaskBinding
import kotlinx.android.synthetic.main.activity_add_task.view.*
import kotlinx.coroutines.CoroutineScope

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            imgRed.setOnClickListener {
                imgRed.setBackgroundColor(Color.RED)
            }
            imgGreen.setOnClickListener {
                imgGreen.setBackgroundColor(Color.GREEN)
            }
            imgYellow.setOnClickListener {
                imgYellow.setBackgroundColor(Color.YELLOW)
            }

            var taskName = taskET.text.toString()
            var taskSubject = taskET.text.toString()
            if (taskName.isNotEmpty()&&taskSubject.isNotEmpty()){

            }





        }








    }//end create



}// end main