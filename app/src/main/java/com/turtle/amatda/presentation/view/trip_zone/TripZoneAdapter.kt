package com.turtle.amatda.presentation.view.trip_zone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemCreateBinding
import com.turtle.amatda.databinding.ListItemTripZoneBinding
import com.turtle.amatda.domain.model.ListItemType
import com.turtle.amatda.domain.model.TripZone
import com.turtle.amatda.domain.model.TripZoneItem

class TripZoneAdapter constructor(
    private val clickAddArea: () -> (Unit),
    private val deleteArea: (TripZone) -> (Unit),
    private val editArea: (TripZone) -> (Unit)
) : ListAdapter<TripZoneItem, RecyclerView.ViewHolder>(
    TripZoneDiffCallback()
) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).listItemType.itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ListItemType.ITEM.itemType) // 아이템
            TripZoneViewHolder(
                ListItemTripZoneBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else // 아이템 생성 버튼
            CreateItemViewHolder(
                ListItemCreateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (item.listItemType == ListItemType.ITEM) { // 아이템
            (holder as TripZoneViewHolder).bind(item.item as TripZone)
        } else { // 아이템 생성 버튼
            (holder as CreateItemViewHolder).bind(item.item as String)
        }
    }

    inner class TripZoneViewHolder(
        private val binding: ListItemTripZoneBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tripZone: TripZone) {
            binding.apply {
                tvTripZoneAddr.text = "${tripZone.area}\n(${tripZone.addr})"
                tvTripZoneType.text = tripZone.zoneType.zone
                tvListTripZoneMemo.text = tripZone.title
                btnTripZoneDelete.setOnClickListener {
                    deleteArea(tripZone)
                }
                setEditClick {
                    editArea(tripZone)
                }
                executePendingBindings()
            }
        }

    }

    inner class CreateItemViewHolder(
        private val binding: ListItemCreateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.apply {
                btnItemCreate.text = title
                btnItemCreate.setOnClickListener {
                    clickAddArea()
                }
                executePendingBindings()
            }
        }

    }

}

class TripZoneDiffCallback : DiffUtil.ItemCallback<TripZoneItem>() {

    override fun areItemsTheSame(oldItem: TripZoneItem, newItem: TripZoneItem): Boolean {
        return oldItem.listItemType == newItem.listItemType && oldItem.item == newItem.item
    }

    override fun areContentsTheSame(oldItem: TripZoneItem, newItem: TripZoneItem): Boolean {
        return false
    }
}