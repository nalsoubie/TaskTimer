package com.example.tasktimer.ViewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimer.Model.TaskTable
import com.example.tasktimer.View.MainActivity
import com.example.tasktimer.databinding.ItemRowBinding


class TasksRV(var clickListner: ClickListner ):RecyclerView.Adapter<TasksRV.ViewHolder>() {
    private var taskList= listOf<TaskTable>()


    class ViewHolder(var binding:ItemRowBinding): RecyclerView.ViewHolder(binding.root){ }
    lateinit var mainActivityViewModel: MainViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var task =taskList[position]

        //mainActivityViewModel = ViewModelProvider(Actvitiy).get(MainViewModel::class.java)
        holder.binding.apply {
            tilteTxt.setText("${task.taskName}")
            subjectTxt.setText("${task.taskDescription}")

            cardView.setOnClickListener {
                clickListner.startTime(task)
            }

            pausebtn.setOnClickListener {
                clickListner.pauseTime(task)
            }

        }
    }

    override fun getItemCount()= taskList.size

    fun update(newlist : List<TaskTable>){
        taskList = newlist
        notifyDataSetChanged()
    } //end update

    interface ClickListner{
        fun startTime(task:TaskTable)
        fun pauseTime(task:TaskTable)
        //fun startTime(running:Boolean, tasktiming:Long, pk:Int)
        //fun pauseTime(running:Boolean, tasktiming:Long)
    }




} //end RV