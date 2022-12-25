package com.example.tasktimer.Model

import android.os.SystemClock
import android.util.Log
import com.example.tasktimer.View.MainActivity


class Timer(var context: MainActivity,var task:TaskTable) {
    companion object {
        val totalTime = 0L
    }

    var taskTime = task.taskTime

    // check if the task is running
    var running = task.isRunning






    fun startTimer() {
        if (running == false) {
            context!!.runOnUiThread {
                if (running == false) {
                    var xy = context.taskT.setBase(SystemClock.elapsedRealtime() - taskTime)
                    context.taskT.setBase(SystemClock.elapsedRealtime() - taskTime)
                    Log.d("TESTTIME","$xy")
                    context.taskT.start()
                    running = true


                }// if
            } //context
        } else {
            Log.d("TAGTAG", "123")// start fun
        }
    }


    fun pauseTimer() {
        context!!.runOnUiThread {

            if (running) {
                context.taskT.stop()
                taskTime = SystemClock.elapsedRealtime() - context.taskT.getBase()
                running = false
            }//if
        }//context
    }

    fun restart() {
        context.taskT.setBase(SystemClock.elapsedRealtime())
        taskTime = 0

    }
}// pause fun

