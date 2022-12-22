package com.example.tasktimer.View

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimer.ViewModel.MainViewModel
import com.example.tasktimer.ViewModel.TasksRV
import com.example.tasktimer.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity(), TasksRV.ClickListner {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: TasksRV
      var taskTimer by Delegates.notNull<Long>()

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        rvAdapter = TasksRV(this)
        binding.mainRV.adapter = rvAdapter
        binding.counter.setOnClickListener {
            timer()
        }
        viewModel.getTasks().observe(this, { taskslist ->
            rvAdapter.update(taskslist)
        })

        binding.apply {


            bAdd.setOnClickListener {
                //intentToAddTask()
                timer()
            }
        }


    }//end create

    fun intentToAddTask() {
        var intent = Intent(this, AddTaskActivity::class.java)
        startActivity(intent)

    }

    fun timer(){
        object : CountDownTimer(100000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var time=millisUntilFinished / 1000
                time = time * -1 + 100
                taskTimer = time
                binding.counter.text = "Time: $time"
                Log.d("timer", "$time ")
                Log.d("timertask", "$taskTimer ")
            }

            override fun onFinish() {
                binding.counter.text = "Time: --"

            }
        }.start()
    }

    //________________________________________________________/

}