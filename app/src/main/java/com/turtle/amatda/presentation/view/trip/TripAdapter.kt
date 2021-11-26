package com.turtle.amatda.presentation.view.trip

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemTripBinding
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringyyMMddTimeStampWithSlash
import com.turtle.amatda.presentation.utilities.extensions.getCountDay
import java.util.*

class TripAdapter constructor(
    private val clickTrip: (Trip) -> (Unit),
    private val deleteTrip: (Trip) -> (Unit),
    private val editTrip: (Trip) -> (Unit)
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
                tvTripType.text = "(${trip.type.concept})"
                tvTripIsGoingOn.text = when {
                    Date().before(trip.date_start) -> {
                        tvTripIsGoingOn.background =
                            GradientDrawable().apply { setColor(0xFF888888.toInt()) }
                        ratingbarTripRating.visibility = View.GONE
                        "D-${trip.date_start.getCountDay(Date())}"
                    }
                    Date().after(trip.date_end) -> {
                        tvTripIsGoingOn.background =
                            GradientDrawable().apply { setColor(0xFF000000.toInt()) }
                        ratingbarTripRating.visibility = View.VISIBLE
                        "여행 종료됨"
                    }
                    else -> {
                        tvTripIsGoingOn.background =
                            GradientDrawable().apply { setColor(0xFFFF6E40.toInt()) }
                        ratingbarTripRating.visibility = View.GONE
                        "여행 진행중"
                    }
                }
                tvListTripHasReminder.text = trip.zoneList.size.toString()
                tvTripDate.text =
                    "${trip.date_start.convertDateToStringyyMMddTimeStampWithSlash()} ~ ${trip.date_end.convertDateToStringyyMMddTimeStampWithSlash()}"
                ratingbarTripRating.rating = trip.rating.toFloat()
                ratingbarTripRating.setOnRatingBarChangeListener { _, rating, _ ->
                    ratingbarTripRating.rating = rating
                }

                setClickListener {
                    clickTrip( // 클릭한 trip 정보 넘기기
                        trip
                    )
                }
                setDeleteClickListener {
                    deleteTrip(
                        trip
                    )
                }
                setEditClickListener {
                    editTrip(
                        trip
                    )
                }

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
        return oldPocket.title == newPocket.title &&
                oldPocket.date_start == newPocket.date_start &&
                oldPocket.date_end == newPocket.date_end &&
                oldPocket.type == newPocket.type

    }
}