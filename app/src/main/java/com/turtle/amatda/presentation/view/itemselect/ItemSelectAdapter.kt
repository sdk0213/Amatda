package com.turtle.amatda.presentation.view.itemselect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemItemselectBinding
import com.turtle.amatda.domain.model.Item

class ItemSelectAdapter : ListAdapter<Item, ItemSelectAdapter.ItemViewHolder>(
    PlantDiffCallback()
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

    class ItemViewHolder(
        private val binding: ListItemItemselectBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.apply {
                this.item = item
                setClickListener {
                    val direction =
                        ItemSelectFragmentDirections.actionViewItemselectFragmentToViewFragment(item.name)
                    it.findNavController().navigate(direction)
                }
                executePendingBindings()
            }
        }
    }

}

class PlantDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}