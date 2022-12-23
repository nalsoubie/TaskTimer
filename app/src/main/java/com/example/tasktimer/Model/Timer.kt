package com.example.tasktimer.Model

import android.os.SystemClock
import com.example.tasktimer.View.MainActivity


class Timer(var context: MainActivity) {

    companion object{
        val totalTime=0L
        var taskTime=0L
        var running=false
    }


    fun startTimer() {
        context!!.runOnUiThread{

            if (running==false) {
                context.taskT.setBase(SystemClock.elapsedRealtime() - taskTime)
                context.taskT.start()
                running = true
            }// if
        } //context
    }// start fun


    fun pauseTimer() {
        context!!.runOnUiThread{

            if (running) {
                context.taskT.stop()
                taskTime = SystemClock.elapsedRealtime() - context.taskT.getBase()
                running = false
            }//if
        }//context
    }
    fun restart(){
        context.taskT.setBase(SystemClock.elapsedRealtime())
        taskTime = 0

}// pause fun



}