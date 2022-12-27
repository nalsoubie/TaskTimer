package com.example.tasktimer.ViewModel



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimer.Model.TaskTable
import com.example.tasktimer.R
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
            tilteTxt.text=("${task.taskName}")
            subjectTxt.setText("${task.taskDescription}")
            if (task.priority == "0red"){
                perority.setImageResource(R.drawable.high_priority)

            }
            if (task.priority == "2green"){

                perority.setImageResource(R.drawable.low_priority)
            }
            if (task.priority == "1yellow"){

                perority.setImageResource(R.drawable.med_priority)
            }

            cardView.setOnClickListener {
                clickListner.startTime(task,taskList)
                clickListner.TotalTime()
                var hours = task.taskTime / 1000/60/60 %24
                var minutes = task.taskTime/1000/60 %60
                var secunds = task.taskTime/ 1000 %60
                var time = StringBuilder()
                time.append(hours).append(":").append(minutes).append(":").append(secunds)
                timerTxt.text= convertSecondsToHMmSs(task.taskTime)

              //  tilteTxt.setText("${task.taskName} is running")

            }
            pausebtn.setOnClickListener {
                clickListner.pauseTime(task)
                var hours = task.taskTime / 1000/60/60 %24
                var minutes = task.taskTime/1000/60 %60
                var secunds = task.taskTime/ 1000 %60
                var time = StringBuilder()
                time.append(hours).append(":").append(minutes).append(":").append(secunds)
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
       // fun convertSecondsToHMmSs(miliSec: Long):String

    }// interface

} //end RV



























fun convertSecondsToHMmSs(miliSec: Long): String {
    var seconds = miliSec / 1000
    val s = seconds % 60
    val m = seconds / 60 % 60
    val h = seconds / (60 * 60) % 24
    return String.format("%d:%02d:%02d", h, m, s)
}