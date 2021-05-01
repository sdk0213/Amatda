package com.turtle.amatda.presentation.view.itemselect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemItemselectBinding
import com.turtle.amatda.domain.model.Item
import javax.inject.Inject

class ItemSelectAdapter @Inject constructor() : ListAdapter<Item, RecyclerView.ViewHolder>(
    PlantDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ListItemItemselectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemData = getItem(position)
        if (itemData != null) {
            (holder as ItemViewHolder).bind(itemData)
        }
    }

    class ItemViewHolder(
        private val binding: ListItemItemselectBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.apply {
                this.item = item
                executePendingBindings()
            }
        }
    }

}

class PlantDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}