package com.example.tasktimer.ViewModel

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimer.Model.TaskTable
import com.example.tasktimer.databinding.ViewRvBinding


//import com.example.tasktimer.databinding.ItemRowBinding

class ChartRV (private val context: Context, private var taskList: List<TaskTable>): RecyclerView.Adapter<ChartRV.ViewHolder>() {


    class ViewHolder(var binding: ViewRvBinding): RecyclerView.ViewHolder(binding.root){ }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ViewRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var task =taskList[position]


        //mainActivityViewModel = ViewModelProvider(Actvitiy).get(MainViewModel::class.java)
        holder.binding.apply {
            title.setText("${task.taskName}")
            subjectTxt.setText("${task.taskDescription}")

            //timerTxt.getBase() +-
            //

            //timerTxt.setBase(SystemClock.elapsedRealtime())
//            cardView.setOnClickListener {
//                //timerTxt.setBase(SystemClock.elapsedRealtime()- task.taskTime)
//                //
//                //if(task.isRunning==false){
//
//                clickListner.startTime(task,taskList)
//                var hours = task.taskTime / 1000/60/60 %24
//                var minutes = task.taskTime/1000/60 %60
//                var secunds = task.taskTime/ 1000 %60
//                var time = StringBuilder()
//                time.append(hours).append(":").append(minutes).append(":").append(secunds)
//                timerTxt.text= time.toString()
//
//
//
//
//                //timerTxt.text= task.taskTime.toString()
//                //timerTxt.
//                //timerTxt.base = SystemClock.elapsedRealtime() - (task.taskTime)
//                //}
//                // else{
//                // Toast.makeText()
//                //Log.d("TAG", "is running ${task.isRunning} ")
//                // }
//            }
//            pausebtn.setOnClickListener {
//                clickListner.pauseTime(task)
//                var hours = task.taskTime / 1000/60/60 %24
//                var minutes = task.taskTime/1000/60 %60
//                var secunds = task.taskTime/ 1000 %60
//                var time = StringBuilder()
//                if (secunds<10 ){
//                    time.append(hours).append(":").append(minutes).append(":").append("0").append(secunds)
//
//                }else{
//                    time.append(hours).append(":").append(minutes).append(":").append(secunds)
//                }
//                time.append(hours).append(":").append(minutes).append(":").append(secunds)
//                timerTxt.text= time.toString()
//                //timerTxt.base = SystemClock.elapsedRealtime() - (task.taskTime)
//            }
//            restarbtn.setOnClickListener {
//                clickListner.restartTime(task)
//                //timerTxt.setBase(SystemClock.elapsedRealtime())
//                ///timerTxt.base = SystemClock.elapsedRealtime() - (task.taskTime)
//            }
//
//            options.setOnClickListener {
//                clickListner.popUpMenu(task)
//            }
//
       }
    }

    override fun getItemCount()= taskList.size

    fun update(newlist : List<TaskTable>){
        taskList = newlist
        notifyDataSetChanged()
    } //end update
}