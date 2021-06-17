package com.turtle.amatda.presentation.view.itemselect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemItemselectBinding
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.model.Todo

class ItemSelectAdapter constructor(
    private val itemClick: (Todo) -> Unit
) : ListAdapter<Item, ItemSelectAdapter.ItemViewHolder>(
    ItemDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemItemselectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        private val binding: ListItemItemselectBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.item = item
            binding.setClickListener {
                itemClick(
                    Todo(
                        title = item.name,
                        subTitle = "${item.name} SubTitle"
                    )
                )
                it.findNavController().navigateUp()
            }
            binding.apply {
                this.item = item
                executePendingBindings()
            }
        }
    }

}

class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}