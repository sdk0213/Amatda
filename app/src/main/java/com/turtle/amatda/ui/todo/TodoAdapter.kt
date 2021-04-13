package com.turtle.amatda.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.data.Todo
import com.turtle.amatda.databinding.ListItemTodoBinding
import javax.inject.Inject

class TodoAdapter @Inject constructor(): ListAdapter<Todo, RecyclerView.ViewHolder>(
    PlantDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TodoViewHolder(
            ListItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val todoData = getItem(position)
        if (todoData != null) {
            (holder as TodoViewHolder).bind(todoData)
        }
    }

    class TodoViewHolder(
        private val binding: ListItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.todo?.let { todo ->
                    todo.checked = !todo.checked
                }
            }
        }

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
        return oldItem.doId== newItem.doId
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}