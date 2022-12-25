package com.example.tasktimer.View

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimer.Model.TaskTable
import com.example.tasktimer.Model.Timer
import com.example.tasktimer.ViewModel.MainViewModel
import com.example.tasktimer.ViewModel.TasksRV
import com.example.tasktimer.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), TasksRV.ClickListner {
    private lateinit var binding: ActivityMainBinding

    private lateinit var rvAdapter: TasksRV
    var totalTime = ""
    var lastTask = TaskTable(2,"2","da",5,"a",false)


    lateinit var taskT: Chronometer
    //lateinit var timer: Timer

    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskT=binding.timer
        //timer = Timer(this)

        rvAdapter = TasksRV(this)
        binding.rvItems.adapter = rvAdapter

        lastTask = TaskTable(2,"2","da",5,"a",false)
        viewModel.getTasks().observe(this, { taskslist ->
            rvAdapter.update(taskslist)
        }) //view model


        binding.apply {
            bAdd.setOnClickListener {
                //timer.startTimer()
                intentToAddTask()
            }// add btn
            taskName.setOnClickListener {
                //timer.restart()
            }

            showAll.setOnClickListener {
                totalTime = taskT.text.toString()
                //timer.pauseTimer()

                Log.d("checkthis","$totalTime")
            } //show all btn
        }// apply



    }//end create

    fun intentToAddTask() {
        var intent = Intent(this, AddTaskActivity::class.java)
        startActivity(intent)

    }

//    fun TimerFun(){
//        object : CountDownTimer(100000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                var time=millisUntilFinished / 1000
//                time = time * -1 + 100
//                taskTimer = time
//                binding.timer.text  = time.toString()
//                binding.timer.timer
//                //binding.counter.text = "Time: $time"
//                Log.d("timer", "$time ")
//                Log.d("timertask", "$taskTimer ")
//            }
//
//            override fun onFinish() {
//                //binding.counter.text = "Time: --"
//
//            }
//        }.start()
//    }

    //________________________________________________________/
    fun splitTime(num:Long):String{
        var hours:Int = (num /60).toInt()
        var minute = num /60
        var sec = num

        if (sec.toInt() == 60){
        }
        return " "
    }

    override fun startTime(task: TaskTable, list: List<TaskTable>) {
        Log.d("ds","sd")


        CoroutineScope(Dispatchers.IO).launch {
            var list = async {
                viewModel.getTasksBloean()
            }.await()
            for (i in list){
                if (i.isRunning == true){
                    lastTask = i
                    Log.d("TAG0100","$lastTask,$i")
                    pauseTime(lastTask)
                    // fun pause that object
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@MainActivity,"you ${lastTask.taskName}",Toast.LENGTH_LONG).show()
                    }
                }else{
                    var timer = Timer(this@MainActivity,task)
                    timer.running=task.isRunning
                    timer.taskTime=task.taskTime
                    timer.startTimer()
                    task.isRunning = true
                    viewModel.updateTask(task)
                    Log.d("TAG2", "$task ")
                    if (task.isRunning == false){
                        taskT.stop()
                    }
                }
            }
        }

/*
1- check is running OB
2- pasue (create the last task Var)
3
 */
//        var timer = Timer(this,task)
//        timer.running=task.isRunning
//        timer.taskTime=task.taskTime
//        timer.startTimer()
//        task.isRunning=timer.running
//        viewModel.updateTask(task)
//        Log.d("TAG2", "$task ")
    }

    override fun pauseTime(task: TaskTable) {
        lastTask = task
        var timer = Timer(this,task)
        timer.running=task.isRunning
        timer.taskTime=task.taskTime
        timer.pauseTimer()
        //taskT.stop() / EXtra
        task.isRunning = false
        task.taskTime=   SystemClock.elapsedRealtime() - taskT.getBase() //
        //task.isRunning=timer.running //Extra
        viewModel.updateTask(task)
        Log.d("TAG1", "$task ")
        if (task.isRunning == false){
            taskT.stop()
        }
    }

    override fun restartTime(task: TaskTable) {

        var timer = Timer(this,task)
        timer.taskTime=task.taskTime
        timer.restart()

        task.taskTime=timer.taskTime
        viewModel.updateTask(task)

        Log.d("restart", "$task ")
    }





}