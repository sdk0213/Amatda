package com.turtle.amatda.presentation.view.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turtle.amatda.databinding.ListItemHomeRestaurantBinding
import com.turtle.amatda.databinding.ListItemHomeTourBinding
import com.turtle.amatda.databinding.ListItemHomeWeatherBinding
import com.turtle.amatda.domain.model.Tour
import com.turtle.amatda.domain.model.Weather
import com.turtle.amatda.presentation.utilities.extensions.convertDateToStringMMddHHmmTimeStamp
import javax.inject.Inject

class HomeRestaurantAdapter constructor(
    private val context: Context
) :
    ListAdapter<Tour, HomeRestaurantAdapter.HomeTourViewHolder>(
        HomeTourDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTourViewHolder {
        return HomeTourViewHolder(
            ListItemHomeRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeTourViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeTourViewHolder(
        private val binding: ListItemHomeRestaurantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Tour) {
            binding.apply {
                tvListItemHomeRestaurantTitle.text = item.title
                tvListItemHomeRestaurantAddr.text = item.addr1
                tvListItemHomeRestaurantType.text = item.cat3.let {
                    when(it){
                        "A05020100" -> "(한식)"
                        "A05020200" -> "(서양식)"
                        "A05020300" -> "(일식)"
                        "A05020400" -> "(중식)"
                        "A05020500" -> "(아시아식)"
                        "A05020600" -> "(패밀리레스토랑)"
                        "A05020700" -> "(이색음식점)"
                        "A05020800" -> "(채식전문점)"
                        "A05020900" -> "(바/까페)"
                        "A05021000" -> "(클럽)"
                        else -> "(분류없음)"
                    }
                }
                cardViewRestaurant.setOnClickListener {
                    context.startActivity(Intent(Intent.ACTION_WEB_SEARCH).putExtra(SearchManager.QUERY, item.title))
                }
                executePendingBindings()
            }
        }

    }

}