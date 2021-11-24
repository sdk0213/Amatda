package com.turtle.amatda.presentation.view.carrier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemCarrierBinding
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierWithPocketAndItems
import com.turtle.amatda.presentation.utilities.extensions.convertToDatePretty

class CarrierAdapter constructor(
    private val clickCarrier: (Carrier) -> (Unit),
    private val editCarrier: (Carrier) -> (Unit),
    private val deleteCarrier: (Carrier) -> (Unit)
) : ListAdapter<CarrierWithPocketAndItems, CarrierAdapter.CarrierViewHolder>(
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

        fun bind(carrierWithPocketAndItems: CarrierWithPocketAndItems) {
            binding.apply {
                binding.tvCarrierName.text = "이름 : ${carrierWithPocketAndItems.carrier.name}"
                binding.tvCarrierType.text =
                    "종류 : ${carrierWithPocketAndItems.carrier.type}(${carrierWithPocketAndItems.carrier.size})"
                binding.tvCarrierDate.text =
                    "${carrierWithPocketAndItems.carrier.date.convertToDatePretty()}"
                binding.tvListCarrierHasPocket.text =
                    "${carrierWithPocketAndItems.pocketAndItems.size} 개"
                var hasPocket = 0
                carrierWithPocketAndItems.pocketAndItems.forEach {
                    hasPocket += it.items.size
                }
                binding.tvListCarrierHasItem.text = "$hasPocket 개"
                setClickListener {
                    clickCarrier(carrierWithPocketAndItems.carrier)
                }
                setEditClickListener {
                    editCarrier(carrierWithPocketAndItems.carrier)
                }
                setDeleteClickListener {
                    deleteCarrier(carrierWithPocketAndItems.carrier)
                }
                executePendingBindings()
            }
        }
    }

}

class CarrierDiffCallback : DiffUtil.ItemCallback<CarrierWithPocketAndItems>() {

    override fun areItemsTheSame(
        oldPocket: CarrierWithPocketAndItems,
        newPocket: CarrierWithPocketAndItems
    ): Boolean {
        return oldPocket.carrier.id == newPocket.carrier.id
    }

    override fun areContentsTheSame(
        oldPocket: CarrierWithPocketAndItems,
        newPocket: CarrierWithPocketAndItems
    ): Boolean {
        return oldPocket.carrier == newPocket.carrier
    }
}