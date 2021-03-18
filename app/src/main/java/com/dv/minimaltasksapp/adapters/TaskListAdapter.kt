package com.dv.minimaltasksapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dv.minimaltasksapp.data.Task
import com.dv.minimaltasksapp.databinding.TaskRowBinding

class TaskListAdapter(private val listener: TaskViewHolderListener) :
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        if (position == itemCount - 1) {
            listener.onLastItemViewed()
        } else if (position == 0) {
            listener.onFirstItemViewed()
        }

        val task = getItem(position)

        holder.bind(task)
    }

    class TaskViewHolder(private val binding: TaskRowBinding, private val listener: TaskViewHolderListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.task = task
            binding.listener = listener
        }
    }

    interface TaskViewHolderListener {
        fun onItemClicked(task: Task)
        fun onLastItemViewed()
        fun onFirstItemViewed()
    }
}

class TaskItemDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}