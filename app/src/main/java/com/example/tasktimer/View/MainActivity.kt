package com.example.tasktimer.View



import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
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
    var lastTask = TaskTable(2, "2", "da", 5, "a", false)


    lateinit var taskT: Chronometer


    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        TotalTime()


        taskT = binding.timer


        rvAdapter = TasksRV(this)
        binding.rvItems.adapter = rvAdapter

        lastTask = TaskTable(2, "2", "da", 5, "a", false)
        viewModel.getTasks().observe(this, { taskslist ->
            rvAdapter.update(taskslist)
        }) //view model


        binding.apply {
            bAdd.setOnClickListener {
                intentToAddTask()
            }// add btn

            showAll.setOnClickListener {

                var intent = Intent(this@MainActivity, ChartTasks_Activity::class.java)
                startActivity(intent)
            } //show all btn
        }// apply


    }//end create


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1,menu)
        return super.onCreateOptionsMenu(menu)
    }// inflater

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.bypriority -> viewModel.getTasks().observe(this, { taskslist ->
                rvAdapter.update(taskslist)
            })
            R.id.byAlphatical -> viewModel.getTasksByAlpha().observe(this, { taskslist ->
                rvAdapter.update(taskslist)
            })
            R.id.byID -> viewModel.getTasksByID().observe(this, { taskslist ->
                rvAdapter.update(taskslist)
            })
        }
        return super.onOptionsItemSelected(item)
    }// menu end

    fun intentToAddTask() {
        var intent = Intent(this, AddTaskActivity::class.java)
        startActivity(intent)

    }

    //________________________________________________________/

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
                            " ${lastTask.taskName}",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.taskName.setText("${task.taskName} has started")
                        binding.taskName.setTextColor(Color.parseColor("#c0b3c2"))
                    }// context
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
                    }// if
                }// else
            }//for

        }// launch
    }// start fun

    override fun pauseTime(task: TaskTable) {
        lastTask = task
        var timer = Timer(this, task)
        timer.running = task.isRunning
        timer.taskTime = task.taskTime
        timer.pauseTimer()

        task.isRunning = false
        task.taskTime = SystemClock.elapsedRealtime() - taskT.getBase() //

        viewModel.updateTask(task)
        Log.d("TAG1", "$task ")
        if (task.isRunning == false) {
            taskT.stop()
        } //if

        binding.taskName.setText("No task has started")
        binding.taskName.setTextColor(Color.parseColor("#afc2cb"))


    }// pause fun

    override fun restartTime(task: TaskTable) {

        var timer = Timer(this, task)
        timer.taskTime = task.taskTime
        timer.restart()

        task.taskTime = timer.taskTime
        viewModel.updateTask(task)
        Log.d("restart", "$task ")


        binding.taskName.setText("No task has started")
        binding.taskName.setTextColor(Color.parseColor("#afc2cb"))

    } //restart fun


    override fun popUpMenu(task: TaskTable) {
        var color = ""
        val inflter = LayoutInflater.from(this)
        val layout = inflter.inflate(R.layout.dialog_pop, null)
        val editTitle = layout.findViewById<EditText>(R.id.taskET)
        val editDesc = layout.findViewById<EditText>(R.id.descriptionET)
        editTitle.setText(task.taskName)
        editDesc.setText(task.taskDescription)
        val refPri = layout.findViewById<Button>(R.id.imgRed)
        val greenPri = layout.findViewById<Button>(R.id.imgGreen)
        val yellowPri = layout.findViewById<Button>(R.id.imgYellow)
        refPri.setOnClickListener {
            refPri.setBackgroundColor(Color.parseColor("#ff5d73"))
            color = "0red"
            greenPri.setBackgroundColor(Color.GRAY)
            yellowPri.setBackgroundColor(Color.GRAY)
        }
        greenPri.setOnClickListener {
            greenPri.setBackgroundColor(Color.parseColor("#b7ffc4"))
            color = "2green"
            refPri.setBackgroundColor(Color.GRAY)
            yellowPri.setBackgroundColor(Color.GRAY)
        }
        yellowPri.setOnClickListener {
            yellowPri.setBackgroundColor(Color.parseColor("#fff6c6"))
            color = "1yellow"
            refPri.setBackgroundColor(Color.GRAY)
            greenPri.setBackgroundColor(Color.GRAY)
        }
        when{
            task.priority=="0red" ->refPri.setBackgroundColor(Color.RED)
            task.priority=="1yellow" ->yellowPri.setBackgroundColor(Color.YELLOW)
            task.priority=="2green" ->greenPri.setBackgroundColor(Color.GREEN)
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
                        TotalTime()
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
                                TotalTime()
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
                    var x = convertSecondsToHMmSs(totalTime)
                    Log.d("ABCD123","$totalTime")

                    binding.total.text = x
                }// context
            }// for


        } //main
    }// fun total
     fun convertSecondsToHMmSs(miliSec: Long): String {
        var seconds = miliSec / 1000
        val s = seconds % 60
        val m = seconds / 60 % 60
        val h = seconds / (60 * 60) % 24
        return String.format("%d:%02d:%02d", h, m, s)
    }// convert


}//main