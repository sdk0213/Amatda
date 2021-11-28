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
import com.turtle.amatda.R
import com.turtle.amatda.databinding.ListItemHomeTourBinding
import com.turtle.amatda.domain.model.Tour

class HomeTourAdapter constructor(
    private val context: Context
) :
    ListAdapter<Tour, HomeTourAdapter.HomeTourViewHolder>(
        HomeTourDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTourViewHolder {
        return HomeTourViewHolder(
            ListItemHomeTourBinding.inflate(
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
        private val binding: ListItemHomeTourBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Tour) {
            binding.apply {
                tvListItemHomeTourTitle.text = item.title
                Glide.with(context)
                    .load(item.firstimage)
                    .placeholder(R.drawable.external_image_ic_external_image_loading)
                    .into(imgViewListItemHomeTourImage)
                cardViewTour.setOnClickListener {
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

class HomeTourDiffCallback : DiffUtil.ItemCallback<Tour>() {

    override fun areItemsTheSame(oldItem: Tour, newItem: Tour): Boolean {
        return oldItem.addr1 == newItem.addr1
    }

    override fun areContentsTheSame(oldItem: Tour, newItem: Tour): Boolean {
        return oldItem.addr1 == newItem.addr1
    }
}