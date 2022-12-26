package com.example.tasktimer.ViewModel


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimer.Model.TaskTable
import com.example.tasktimer.databinding.ItemRowBinding


class TasksRV(var clickListner: ClickListner ):RecyclerView.Adapter<TasksRV.ViewHolder>() {
    private var taskList= listOf<TaskTable>()

    class ViewHolder(var binding:ItemRowBinding): RecyclerView.ViewHolder(binding.root){ }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var task =taskList[position]
        holder.binding.apply {
            tilteTxt.setText("${task.taskName}")
            subjectTxt.setText("${task.taskDescription}")
            timerTxt.text= convertSecondsToHMmSs(task.taskTime)
//            if (task.priority == "0red"){
//                perority.setBackgroundColor(Color.parseColor("#DA341D"))
//            }
//            if (task.priority == "2green"){
//                perority.setBackgroundColor(Color.GREEN)
//            }
//            if (task.priority == "1yellow"){
//                perority.setBackgroundColor(Color.YELLOW)
//            }
            cardView.setOnClickListener {
                clickListner.startTime(task,taskList)
                clickListner.TotalTime()
                timerTxt.text= convertSecondsToHMmSs(task.taskTime)
            }
            pausebtn.setOnClickListener {
                clickListner.pauseTime(task)
                timerTxt.text= convertSecondsToHMmSs(task.taskTime)
                clickListner.TotalTime()
            }
            restarbtn.setOnClickListener {
                clickListner.restartTime(task)
                clickListner.TotalTime()
                timerTxt.text= convertSecondsToHMmSs(task.taskTime)
            }
            options.setOnClickListener {
                clickListner.popUpMenu(task)
            }
        }
    }
    override fun getItemCount()= taskList.size
    fun update(newlist : List<TaskTable>){
        taskList = newlist
        notifyDataSetChanged()
    } //end update

    interface ClickListner{
        fun startTime(task:TaskTable,list:List<TaskTable>)
        fun pauseTime(task:TaskTable)
        fun restartTime(task: TaskTable)
        fun popUpMenu(task: TaskTable)
        fun TotalTime()
        //fun convertSecondsToHMmSss(miliSec: Long):String
        //fun startTime(running:Boolean, tasktiming:Long, pk:Int)
        //fun pauseTime(running:Boolean, tasktiming:Long)
    }// interface

} //end RV























fun convertSecondsToHMmSs(miliSec: Long): String {
    var seconds = miliSec / 1000
    val s = seconds % 60
    val m = seconds / 60 % 60
    val h = seconds / (60 * 60) % 24
    return String.format("%d:%02d:%02d", h, m, s)
}