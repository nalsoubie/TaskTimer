package com.example.tasktimer.View

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimer.Model.TaskTable
import com.example.tasktimer.R
import com.example.tasktimer.ViewModel.MainViewModel
import com.example.tasktimer.databinding.ActivityAddTaskBinding


class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    var color = " "
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val notificationId = 1234
    private val channelId = "myapp.notifications"
    private val description = "Notification App Example"


    override fun onCreate(savedInstanceState: Bundle?) {
        //supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            button2.setOnClickListener {
                intentToMain()
            }
//            imgRed.setBackgroundColor(Color.RED)
//            imgGreen.setBackgroundColor(Color.GREEN)
//            imgYellow.setBackgroundColor(Color.YELLOW)

            imgRed.setOnClickListener {
                imgRed.setBackgroundColor(Color.parseColor("#ff5d73"))
                color = "0red"

                imgGreen.setBackgroundColor(Color.GRAY)
                imgYellow.setBackgroundColor(Color.GRAY)
                // try to name it with 0 to 3 to accend in RV
            }
            imgGreen.setOnClickListener {
                imgGreen.setBackgroundColor(Color.parseColor("#b7ffc4"))
                color = "2green"
                //"#b7ffc4"

                imgYellow.setBackgroundColor(Color.GRAY)
                imgRed.setBackgroundColor(Color.GRAY)
            }
            imgYellow.setOnClickListener {
                imgYellow.setBackgroundColor(Color.parseColor("#fff6c6"))
                color = "1yellow"

                imgGreen.setBackgroundColor(Color.GRAY)
                imgRed.setBackgroundColor(Color.GRAY)
            }

            saveButton.setOnClickListener {
                var taskName = taskET.text.toString()
                var taskSubject = descriptionET.text.toString()
                if (taskName.isNotEmpty() && taskSubject.isNotEmpty() && color.length > 1) {
                    var task = TaskTable(0, taskName, taskSubject, 0, color, false)
                    viewModel.addTask(task)
                    createNotification(task)
                    intentToMain()

                }
//                var intentBack = Intent(this@AddTaskActivity,MainActivity::class.java)
//                startActivity(intentBack)
            }


        }


    }//end create

    fun intentToMain(){
        var intentToMain = Intent(this,MainActivity::class.java)
        startActivity(intentToMain)
    }
    fun createNotification(task: TaskTable){
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)

        //creating a pending intent of the intent we created before, in case the user clicked on the notification
        //the pending intent will be waiting until the user clicks on the notification.
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        //creating a notification channel for the notification.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

            //building the notification
            builder = Notification.Builder(this, channelId)
                .setSmallIcon(R.drawable.bi_chart)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.bi_chart))
                .setContentIntent(pendingIntent)
                .setContentTitle("New Task have been Added")
                .setContentText("${task.taskDescription}")
        } else {

            // building the notification
            builder = Notification.Builder(this)
                //.setSmallIcon(R.drawable.ic_notification)
                //.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_notification))
                .setContentIntent(pendingIntent)
                .setContentTitle("New Task have been Added")
                .setContentText("${task.taskDescription}")
        }
        //executing the notification
        notificationManager.notify(notificationId, builder.build())
    }
}// end main