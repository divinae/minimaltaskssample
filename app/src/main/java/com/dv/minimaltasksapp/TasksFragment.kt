package com.dv.minimaltasksapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dv.minimaltasksapp.adapters.TaskListAdapter
import com.dv.minimaltasksapp.data.Task
import com.dv.minimaltasksapp.databinding.TasksFragmentBinding
import com.dv.minimaltasksapp.viewmodels.TasksFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class TasksFragment @Inject constructor() : Fragment() {

    private val viewModel: TasksFragmentViewModel by viewModels()

    private lateinit var binding: TasksFragmentBinding

    private var tasks: List<Task> = emptyList()

    private val taskViewHolderListener = object : TaskListAdapter.TaskViewHolderListener {
        override fun onItemClicked(task: Task) {
            viewModel.toggleTask(task)
        }

        override fun onLastItemViewed() {
            Log.d("kuba_TasksFragment", "onLastItemViewed ")
        }

        override fun onFirstItemViewed() {
            Log.d("kuba_TasksFragment", "onFirstItemViewed ")
        }
    }

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
            viewModel.removeTask(tasks.get(position))
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

        val adapter = TaskListAdapter(taskViewHolderListener).apply {
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

            Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun subscribeTaskList(adapter: TaskListAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getTasks().collect { tasks ->
                    adapter.submitList(tasks)
                    this@TasksFragment.tasks = tasks
                }
            }
        }
    }
}