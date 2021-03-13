package com.dv.minimaltasksapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dv.minimaltasksapp.data.models.Task
import com.dv.minimaltasksapp.databinding.TaskRowBinding
import com.dv.minimaltasksapp.viewmodels.TaskItemViewModel

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    class TaskViewHolder(private val binding: TaskRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.task = task
            binding.viewModel = TaskItemViewModel(task)
        }
    }
}

class TaskItemDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}