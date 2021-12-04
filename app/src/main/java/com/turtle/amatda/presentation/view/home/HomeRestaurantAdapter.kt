package com.turtle.amatda.presentation.view.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.R
import com.turtle.amatda.databinding.ListItemHomeRestaurantBinding
import com.turtle.amatda.domain.model.Tour

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
                    when (it) {
                        "A05020100" -> context.getString(R.string.home_restaurant_korean_food)
                        "A05020200" -> context.getString(R.string.home_restaurant_western_food)
                        "A05020300" -> context.getString(R.string.home_restaurant_japanese_food)
                        "A05020400" -> context.getString(R.string.home_restaurant_chinenes_food)
                        "A05020500" -> context.getString(R.string.home_restaurant_asian_food)
                        "A05020600" -> context.getString(R.string.home_restaurant_family_restaurant)
                        "A05020700" -> context.getString(R.string.home_restaurant_unique_restaurant)
                        "A05020800" -> context.getString(R.string.home_restaurant_vegetarian_restaurant)
                        "A05020900" -> context.getString(R.string.home_restaurant_bar_cafe)
                        "A05021000" -> context.getString(R.string.home_restaurant_club)
                        else -> context.getString(R.string.home_restaurant_restaurant)
                    }
                }
                imageViewHomeRestaurantFood.setImageResource(
                    when (item.cat3) {
                        "A05020100" -> R.drawable.flaticon_com_ic_a05020100
                        "A05020200" -> R.drawable.flaticon_com_ic_a05020200
                        "A05020300" -> R.drawable.flaticon_com_ic_a05020300
                        "A05020400" -> R.drawable.flaticon_com_ic_a05020400
                        "A05020500" -> R.drawable.flaticon_com_ic_a05020500
                        "A05020600" -> R.drawable.flaticon_com_ic_a05020600
                        "A05020700" -> R.drawable.flaticon_com_ic_a05020700
                        "A05020800" -> R.drawable.flaticon_com_ic_a05020800
                        "A05020900" -> R.drawable.flaticon_com_ic_a05020900
                        "A05021000" -> R.drawable.flaticon_com_ic_a05021000
                        else -> R.drawable.flaticon_com_ic_a05029999
                    }
                )
                cardViewRestaurant.setOnClickListener {
                    context.startActivity(
                        Intent(Intent.ACTION_WEB_SEARCH).putExtra(
                            SearchManager.QUERY,
                            item.title
                        )
                    )
                }
                executePendingBindings()
            }
        }

    }

}