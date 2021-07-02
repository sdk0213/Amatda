package com.turtle.amatda.presentation.view.carrier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemCarrierBinding
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierAndGetHasItemNum

class CarrierAdapter constructor(
    private val clickCarrier : (Carrier) -> (Unit)
) : ListAdapter<CarrierAndGetHasItemNum, CarrierAdapter.CarrierViewHolder>(
    CarrierDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrierViewHolder {
        return CarrierViewHolder(
            ListItemCarrierBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarrierViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarrierViewHolder(
        private val binding: ListItemCarrierBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CarrierAndGetHasItemNum) {
            binding.apply {
                carrierAndGetHasItemNum = item
                setClickListener {
                    clickCarrier(item.carrier)
                }
                executePendingBindings()
            }
        }
    }

}

class CarrierDiffCallback : DiffUtil.ItemCallback<CarrierAndGetHasItemNum>() {

    override fun areItemsTheSame(oldItem: CarrierAndGetHasItemNum, newItem: CarrierAndGetHasItemNum): Boolean {
        return oldItem.carrier.id == newItem.carrier.id
    }

    override fun areContentsTheSame(oldItem: CarrierAndGetHasItemNum, newItem: CarrierAndGetHasItemNum): Boolean {
        return oldItem.carrier == newItem.carrier
    }
}