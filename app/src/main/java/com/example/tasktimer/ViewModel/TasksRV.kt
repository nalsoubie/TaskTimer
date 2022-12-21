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
        }
    }

    override fun getItemCount()= taskList.size

    fun update(newlist : List<TaskTable>){
        taskList = newlist
        notifyDataSetChanged()
    } //end update

    interface ClickListner{
//        fun update(note:NotesTable)
//        fun delet(pk: Int)
    }




} //end RV