package com.turtle.amatda.presentation.view.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemTodoBinding
import com.turtle.amatda.domain.model.Todo

class TodoAdapter : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(
    PlantDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            ListItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoViewHolder(
        private val binding: ListItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Todo) {
            binding.apply {
                todo = item
                executePendingBindings()
            }
        }
    }

}

class PlantDiffCallback : DiffUtil.ItemCallback<Todo>() {

    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}