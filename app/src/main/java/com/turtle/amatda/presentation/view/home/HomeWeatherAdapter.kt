package com.turtle.amatda.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemHomeWeatherBinding
import com.turtle.amatda.domain.model.Weather
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringMMddHHmmTimeStamp

class HomeWeatherAdapter constructor() :
    ListAdapter<Weather, HomeWeatherAdapter.HomeWeatherViewHolder>(
        HomeWeatherDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeWeatherViewHolder {
        return HomeWeatherViewHolder(
            ListItemHomeWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeWeatherViewHolder(
        private val binding: ListItemHomeWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Weather) {
            binding.apply {
                tvListItemHomeWeatherDate.text = item.date.convertDateToStringMMddHHmmTimeStamp()
                tvListItemHomeWeatherTmp.text = item.tmp.toString() + "℃"
                tvListItemHomeWeatherPop.text = item.pop.toString() + "%"
                tvListItemHomeWeatherSky.text = item.sky.sky
                tvListItemHomeWeatherPty.text = item.pty.pty
                executePendingBindings()
            }
        }

    }

}

class HomeWeatherDiffCallback : DiffUtil.ItemCallback<Weather>() {

    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.date == newItem.date
    }
}