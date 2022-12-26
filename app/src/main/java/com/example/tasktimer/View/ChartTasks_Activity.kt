package com.example.tasktimer.View


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.tasktimer.Model.TaskTable
import com.example.tasktimer.ViewModel.ChartRV
import com.example.tasktimer.ViewModel.MainViewModel
import com.example.tasktimer.databinding.ActivityChartTasksBinding



class ChartTasks_Activity : AppCompatActivity() {

    lateinit var binding: ActivityChartTasksBinding

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }


    private lateinit var rvAdapter: ChartRV
    private lateinit var tasksList: ArrayList<TaskTable>


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding= ActivityChartTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // btn id = cancelBtn
          binding.cancelBtn.setOnClickListener {
          intentToMain()
                 }
        tasksList= arrayListOf()
        rvAdapter = ChartRV(this,tasksList)
        binding.rvchart.adapter = rvAdapter



        viewModel.getTasks().observe(this, {
            tasksList.addAll(it)
            rvAdapter.update(tasksList)
            setPieChart()
        }) //view model

    }

    private fun setPieChart() {
        val pie = AnyChart.pie()
        val dataEntries = ArrayList<DataEntry>()
        for (i in tasksList) {
            dataEntries.add(ValueDataEntry(i.taskName, (i.taskTime / 1000).toInt()))
        }
        pie.data(dataEntries)
        binding.pieChart.setChart(pie)
    }
    fun intentToMain(){
        var intentToMain = Intent(this,MainActivity::class.java)
        startActivity(intentToMain)
    }
}