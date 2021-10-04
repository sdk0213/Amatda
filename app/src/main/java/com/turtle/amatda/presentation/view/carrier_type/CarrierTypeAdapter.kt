package com.turtle.amatda.presentation.view.carrier_type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemCarrierTypeBinding
import com.turtle.amatda.domain.model.CarrierType

class CarrierTypeAdapter constructor(
    private val clickType: (CarrierType) -> (Unit)
): ListAdapter<CarrierType, CarrierTypeAdapter.CarrierTypeViewHolder>(
    CarrierTypeDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrierTypeViewHolder {
        return CarrierTypeViewHolder(
            ListItemCarrierTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarrierTypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarrierTypeViewHolder(
        private val binding: ListItemCarrierTypeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CarrierType) {
            binding.apply {
                carrierType = item
                setClickListener {
                    clickType(item)
                }
                executePendingBindings()
            }
        }

    }

}

class CarrierTypeDiffCallback : DiffUtil.ItemCallback<CarrierType>() {

    override fun areItemsTheSame(oldItem: CarrierType, newItem: CarrierType): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: CarrierType, newItem: CarrierType): Boolean {
        return oldItem.type == newItem.type
    }
}