package com.turtle.amatda.presentation.view.carrier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemCarrierBinding
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.CarrierAndGetHasPocketNum

class CarrierAdapter constructor(
    private val clickCarrier : (Carrier) -> (Unit),
    private val editCarrier : (Carrier) -> (Unit),
    private val deleteCarrier : (Carrier) -> (Unit)
) : ListAdapter<CarrierAndGetHasPocketNum, CarrierAdapter.CarrierViewHolder>(
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

        fun bind(carrierAndPocket: CarrierAndGetHasPocketNum) {
            binding.apply {
                carrierAndGetHasPocketNum = carrierAndPocket
                setClickListener {
                    clickCarrier(carrierAndPocket.carrier)
                }
                setEditClickListener {
                    editCarrier(carrierAndPocket.carrier)
                }
                setDeleteClickListener {
                    deleteCarrier(carrierAndPocket.carrier)
                }
                executePendingBindings()
            }
        }
    }

}

class CarrierDiffCallback : DiffUtil.ItemCallback<CarrierAndGetHasPocketNum>() {

    override fun areItemsTheSame(oldPocket: CarrierAndGetHasPocketNum, newPocket: CarrierAndGetHasPocketNum): Boolean {
        return oldPocket.carrier.id == newPocket.carrier.id
    }

    override fun areContentsTheSame(oldPocket: CarrierAndGetHasPocketNum, newPocket: CarrierAndGetHasPocketNum): Boolean {
        return oldPocket.carrier == newPocket.carrier
    }
}