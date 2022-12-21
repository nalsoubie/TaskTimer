package com.example.tasktimer.View

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimer.Model.TaskTable
import com.example.tasktimer.ViewModel.MainViewModel
import com.example.tasktimer.databinding.ActivityAddTaskBinding
import kotlinx.android.synthetic.main.activity_add_task.view.*
import kotlinx.coroutines.CoroutineScope

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
    var color = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {



            imgRed.setOnClickListener {
                imgRed.setBackgroundColor(Color.RED)
                color = "0red"
                // try to name it with 0 to 3 to accend in RV
            }
            imgGreen.setOnClickListener {
                imgGreen.setBackgroundColor(Color.GREEN)
                color = "1green"
            }
            imgYellow.setOnClickListener {
                imgYellow.setBackgroundColor(Color.YELLOW)
                color = "2yellow"
            }

            var taskName = taskET.text.toString()
            var taskSubject = taskET.text.toString()
            if (taskName.isNotEmpty()&&taskSubject.isNotEmpty()&&color.length>1){
                var task = TaskTable(0,taskName,taskSubject,0,color,false)
                viewModel.addTask(task)




            }





        }








    }//end create



}// end main