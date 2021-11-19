package com.turtle.amatda.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.R
import com.turtle.amatda.databinding.ListItemHomeWeatherBinding
import com.turtle.amatda.domain.model.Pty
import com.turtle.amatda.domain.model.Sky
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
                val date = item.date.convertDateToStringMMddHHmmTimeStamp()
                tvListItemHomeWeatherDate.text =
                    if (date.split(" ")[1] == "00:00") date.split(" ")[0] else date.split(" ")[1]
                tvListItemHomeWeatherTmp.text = item.tmp.toString() + "℃"
                if (item.pty == Pty.None) {
                    tvListItemHomeWeatherStatus.text = "${item.sky.sky}"
                } else {
                    tvListItemHomeWeatherStatus.text = "${item.pop}% 확률로 ${item.pty.pty}"
                }
                val icon = selectWeatherIcon(item.sky, item.pty)
                imageViewHomeWeatherIcon.setImageResource(icon)
                when (icon) {
                    R.drawable.flaticon_com_ic_fresh -> {
                        tvListItemHomeWeatherDate.setTextColor(0xFFFF6E40.toInt())
                        tvListItemHomeWeatherTmp.setTextColor(0xFFFF6E40.toInt())
                        tvListItemHomeWeatherStatus.setTextColor(0xFFFF6E40.toInt())
                    }
                    R.drawable.flaticon_com_ic_rain -> {
                        tvListItemHomeWeatherDate.setTextColor(0xFF2962FF.toInt())
                        tvListItemHomeWeatherTmp.setTextColor(0xFF2962FF.toInt())
                        tvListItemHomeWeatherStatus.setTextColor(0xFF2962FF.toInt())
                    }
                    R.drawable.flaticon_com_ic_snow -> {
                        tvListItemHomeWeatherDate.setTextColor(0xFF2962FF.toInt())
                        tvListItemHomeWeatherTmp.setTextColor(0xFF2962FF.toInt())
                        tvListItemHomeWeatherStatus.setTextColor(0xFF2962FF.toInt())
                    }
                    else -> {
                        tvListItemHomeWeatherDate.setTextColor(0xFF000000.toInt())
                        tvListItemHomeWeatherTmp.setTextColor(0xFF000000.toInt())
                        tvListItemHomeWeatherStatus.setTextColor(0xFF000000.toInt())
                    }
                }

                executePendingBindings()
            }
        }

    }

    private fun selectWeatherIcon(sky: Sky, pty: Pty): Int =
        if (pty == Pty.Shower || pty == Pty.Rain || pty == Pty.RainOrSnow) {
            R.drawable.flaticon_com_ic_rain
        } else if (pty == Pty.Snow) {
            R.drawable.flaticon_com_ic_snow
        } else if (sky == Sky.Cloudy) {
            R.drawable.flaticon_com_ic_cloudy
        } else if (sky == Sky.Overcast) {
            R.drawable.flaticon_com_ic_overcast
        } else {
            R.drawable.flaticon_com_ic_fresh
        }


    class HomeWeatherDiffCallback : DiffUtil.ItemCallback<Weather>() {

        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.date == newItem.date
        }
    }
}