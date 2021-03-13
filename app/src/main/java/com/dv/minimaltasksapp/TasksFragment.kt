package com.dv.minimaltasksapp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dv.minimaltasksapp.databinding.TaskFragmentBinding
import com.dv.minimaltasksapp.viewmodels.TaskFragmentViewModel

class TasksFragment : Fragment() {

    companion object {
        fun newInstance() = TasksFragment()
    }

    private val viewModel: TaskFragmentViewModel by viewModels()

    private lateinit var binding: TaskFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TaskFragmentBinding.inflate(inflater, container, false)

        val adapter = TaskListAdapter()
        binding.taskList.adapter = adapter
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