package com.turtle.amatda.presentation.view.carrier_size

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemCarrierSizeBinding
import com.turtle.amatda.domain.model.CarrierSize

class CarrierSizeAdapter constructor(
    private val clickSize: (CarrierSize) -> (Unit)
) : ListAdapter<CarrierSize, CarrierSizeAdapter.CarrierSizeViewHolder>(
    CarrierSizeDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrierSizeViewHolder {
        return CarrierSizeViewHolder(
            ListItemCarrierSizeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarrierSizeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarrierSizeViewHolder(
        private val binding: ListItemCarrierSizeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CarrierSize) {
            binding.apply {
                carrierSize = item
                setClickListener {
                    clickSize(item)
                }
                executePendingBindings()
            }
        }
    }

}

class CarrierSizeDiffCallback : DiffUtil.ItemCallback<CarrierSize>() {

    override fun areItemsTheSame(oldItem: CarrierSize, newItem: CarrierSize): Boolean {
        return oldItem.size == newItem.size
    }

    override fun areContentsTheSame(oldItem: CarrierSize, newItem: CarrierSize): Boolean {
        return oldItem.size == newItem.size
    }
}