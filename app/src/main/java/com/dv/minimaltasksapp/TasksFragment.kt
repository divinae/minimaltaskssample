package com.dv.minimaltasksapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dv.minimaltasksapp.databinding.TasksFragmentBinding
import com.dv.minimaltasksapp.viewmodels.TaskFragmentViewModel

class TasksFragment : Fragment() {

    companion object {
        fun newInstance() = TasksFragment()
    }

    private val viewModel: TaskFragmentViewModel by viewModels()

    private lateinit var binding: TasksFragmentBinding

    private val itemTouchHelper = object : SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            // TODO add support for moving elements
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            viewModel.removeTask(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TasksFragmentBinding.inflate(inflater, container, false)

        val linearLayoutManager = LinearLayoutManager(context).apply {
            reverseLayout = true
        }

        binding.taskList.layoutManager = linearLayoutManager

        val adapter = TaskListAdapter().apply {
            registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    binding.taskList.scrollToPosition(positionStart)
                }
            })
        }

        binding.taskList.adapter = adapter
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.taskList)
        subscribeTaskList(adapter)

        binding.addTaskButton.setOnClickListener {
            // TODO create input text dialog
            viewModel.addTask("new task ${adapter.itemCount}")
        }

        return binding.root
    }

    private fun subscribeTaskList(adapter: TaskListAdapter) {
        viewModel.getTasks().observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
        }
    }
}