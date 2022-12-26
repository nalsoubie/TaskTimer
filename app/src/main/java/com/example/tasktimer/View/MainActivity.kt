package com.example.tasktimer.View


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimer.Model.TaskTable
import com.example.tasktimer.Model.Timer
import com.example.tasktimer.R
import com.example.tasktimer.ViewModel.MainViewModel
import com.example.tasktimer.ViewModel.TasksRV
import com.example.tasktimer.databinding.ActivityMainBinding
import kotlinx.coroutines.*



class MainActivity : AppCompatActivity(), TasksRV.ClickListner {
    private lateinit var binding: ActivityMainBinding

    private lateinit var rvAdapter: TasksRV
    var totalTime = ""
    var lastTask = TaskTable(2, "2", "da", 5, "a", false)


    lateinit var taskT: Chronometer
    //lateinit var timer: Timer

    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        TotalTime()

        taskT = binding.timer
        //timer = Timer(this)

        rvAdapter = TasksRV(this)
        binding.rvItems.adapter = rvAdapter

        lastTask = TaskTable(2, "2", "da", 5, "a", false)
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

            total.setOnClickListener {
                totalTime = taskT.text.toString()
                //timer.pauseTimer()
                var intent = Intent(this@MainActivity, ChartTasks_Activity::class.java)
                startActivity(intent)

                Log.d("checkthis", "$totalTime")
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
    fun splitTime(num: Long): String {
        var hours: Int = (num / 60).toInt()
        var minute = num / 60
        var sec = num

        if (sec.toInt() == 60) {
        }
        return " "
    }

    override fun startTime(task: TaskTable, list: List<TaskTable>) {
        Log.d("ds", "sd")


        CoroutineScope(Dispatchers.IO).launch {
            var list = async {
                viewModel.getTasksBloean()
            }.await()
            for (i in list) {
                if (i.isRunning == true) {
                    lastTask = i
                    Log.d("TAG0100", "$lastTask,$i")
                    pauseTime(lastTask)
                    // fun pause that object
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity,
                            "you ${lastTask.taskName}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    var timer = Timer(this@MainActivity, task)
                    timer.running = task.isRunning
                    timer.taskTime = task.taskTime
                    timer.startTimer()
                    task.isRunning = true
                    viewModel.updateTask(task)
                    Log.d("TAG2", "$task ")
                    if (task.isRunning == false) {
                        taskT.stop()
                    }
                }
            }

        }

        //binding.total.text = "Total Time\n${taskT.contentDescription}"

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
        var timer = Timer(this, task)
        timer.running = task.isRunning
        timer.taskTime = task.taskTime
        timer.pauseTimer()
        //taskT.stop() / EXtra
        task.isRunning = false
        task.taskTime = SystemClock.elapsedRealtime() - taskT.getBase() //
        //task.isRunning=timer.running //Extra
        viewModel.updateTask(task)
        Log.d("TAG1", "$task ")
        if (task.isRunning == false) {
            taskT.stop()
        }
        //binding.total.text = "Total Time\n${taskT.contentDescription}"


    }

    override fun restartTime(task: TaskTable) {

        var timer = Timer(this, task)
        timer.taskTime = task.taskTime
        timer.restart()

        task.taskTime = timer.taskTime
        viewModel.updateTask(task)
        Log.d("restart", "$task ")


    }

    override fun popUpMenu(task: TaskTable) {

        var color = ""
        val inflter = LayoutInflater.from(this)
        val layout = inflter.inflate(R.layout.dialog_pop, null)

        val editTitle = layout.findViewById<EditText>(R.id.editTitle)
        val editDesc = layout.findViewById<EditText>(R.id.editDesctiption)

        val refPri = layout.findViewById<ImageButton>(R.id.redPri)
        val greenPri = layout.findViewById<ImageButton>(R.id.greenPri)
        val yellowPri = layout.findViewById<ImageButton>(R.id.yellowPri)
        refPri.setOnClickListener {
            refPri.setBackgroundColor(Color.RED)
            color = "0red"
        }
        greenPri.setOnClickListener {
            greenPri.setBackgroundColor(Color.GREEN)
            color = "1green"
        }
        yellowPri.setOnClickListener {
            yellowPri.setBackgroundColor(Color.YELLOW)
            color = "2yellow"
        }

        val dialogBuilder = AlertDialog.Builder(this)

        val popupMenu = PopupMenu(this, binding.rvItems.findViewById(R.id.options))
        // add the menu
        popupMenu.inflate(R.menu.menu)
        // implement on menu item click Listener
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.editbtn -> {
                        dialogBuilder
                            .setPositiveButton("Edit") { dialog, _ ->

                                if (editTitle.text.toString().isEmpty() && editDesc.text.toString()
                                        .isEmpty()
                                ) {
                                    editTitle.setError("Please provide a name.")
                                    editTitle.requestFocus()
                                    editDesc.setError("Please provide a name.")
                                    editDesc.requestFocus()
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Need ti fill all boxes",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }// if
                                else {
                                    task.taskName = editTitle.text.toString()
                                    task.taskDescription = editDesc.text.toString()
                                    task.priority = color
                                    viewModel.updateTask(task)
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Task has been updated",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                }//else

                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                        val alert = dialogBuilder.create()
                        alert.setTitle("Update Task")
                        alert.setView(layout)
                        alert.show()

                        true
                    }// edit
                    R.id.deletebtn -> {
                        /**set delete*/
                        dialogBuilder
                            .setTitle("Delete Confirmation")
                            .setMessage("Are you sure delete this Task?")
                            .setPositiveButton("Delete") { dialog, _ ->
                                viewModel.deleteTask(task)
                                Toast.makeText(
                                    this@MainActivity,
                                    "Deleted this Task",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    } //delet item
                }//when
                return false
            }// menu on click
        })// obj
        popupMenu.show()


    } //pop up fun.

    override fun TotalTime() {
        var totalTime :Long= 1
        CoroutineScope(Dispatchers.IO).launch {
            var list = async {
                viewModel.getTasksBloean()
            }.await()
            for (i in list) {
                totalTime += i.taskTime
                withContext(Dispatchers.Main) {
//                    var hours = totalTime / 1000/60/60 %24
//                    var minutes = totalTime/1000/60 %60
//                    var secunds = totalTime/ 1000 %60
                    //var z = totalTime / 1000
                    var x = convertSecondsToHMmSs(totalTime)
                    Log.d("ABCD123","$totalTime")
//                    if (secunds<10){
//                        secunds = 0 + secunds
//                    }


                    binding.total.text = x
                }
                //hh:mm:ss
                ;


            }


        } //main
    }
    override fun convertSecondsToHMmSs(miliSec: Long): String {
        var seconds = miliSec / 1000
        val s = seconds % 60
        val m = seconds / 60 % 60
        val h = seconds / (60 * 60) % 24
        return String.format("%d:%02d:%02d", h, m, s)
    }
}