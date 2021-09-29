package com.turtle.amatda.presentation.view.trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemTripBinding
import com.turtle.amatda.domain.model.Trip

class TripAdapter constructor(

) : ListAdapter<Trip, TripAdapter.TripViewHolder>(
    TripDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            ListItemTripBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TripViewHolder(
        private val binding: ListItemTripBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(trip: Trip) {
            binding.apply {
//                carrierAndGetHasPocketNum = carrierAndPocket
//                setClickListener {
//                    clickCarrier(carrierAndPocket.carrier)
//                }
//                setEditClickListener {
//                    editCarrier(carrierAndPocket.carrier)
//                }
//                setDeleteClickListener {
//                    deleteCarrier(carrierAndPocket.carrier)
//                }
                executePendingBindings()
            }
        }
    }

}

class TripDiffCallback : DiffUtil.ItemCallback<Trip>() {

    override fun areItemsTheSame(oldPocket: Trip, newPocket: Trip): Boolean {
        return oldPocket.id == newPocket.id
    }

    override fun areContentsTheSame(oldPocket: Trip, newPocket: Trip): Boolean {
        return oldPocket.id == newPocket.id
    }
}