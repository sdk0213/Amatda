package com.turtle.amatda.presentation.view.trip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemTripBinding
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringyyyyMMddTimeStamp
import com.turtle.amatda.presentation.utilities.extensions.getCountDay
import java.util.*

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
                tvTripName.text = trip.title
                tvTripDDay.text = when {
                    Date().before(trip.date_start) -> "D-${trip.date_start.getCountDay(Date())}"
                    Date().after(trip.date_end) -> {
                        ratingbarTripRating.visibility = View.VISIBLE
                        "여행 종료됨"
                    }
                    else -> "여행 진행중"
                }
                tvTripCourse.text = trip.zoneList.map { it.area }.toList().toString()
                tvTripDate.text =
                    "${trip.date_start.convertDateToStringyyyyMMddTimeStamp()} ~ ${trip.date_end.convertDateToStringyyyyMMddTimeStamp()}"
                ratingbarTripRating.rating = trip.rating.toFloat()
                ratingbarTripRating.setOnRatingBarChangeListener { _, rating, _ ->
                    ratingbarTripRating.rating = rating
                }
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