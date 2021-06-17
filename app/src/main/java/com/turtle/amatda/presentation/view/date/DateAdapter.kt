package com.turtle.amatda.presentation.view.date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemDateBinding
import com.turtle.amatda.domain.model.Todo

class DateAdapter : ListAdapter<Todo, DateAdapter.DateViewHolder>(
    DateDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        return DateViewHolder(
            ListItemDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DateViewHolder(
        private val binding: ListItemDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Todo) {

        }
    }

}

class DateDiffCallback : DiffUtil.ItemCallback<Todo>() {

    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}

