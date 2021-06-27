package com.turtle.amatda.presentation.view.carrier_type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.ListItemCarrierTypeBinding
import com.turtle.amatda.domain.model.CarrierType

class CarrierTypeAdapter constructor(
    private val clickType: (CarrierType) -> (Unit)
): ListAdapter<CarrierType, CarrierTypeAdapter.CarrierTypeViewHolder>(
    CarrierTypeDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrierTypeViewHolder {
        return CarrierTypeViewHolder(
            ListItemCarrierTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarrierTypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarrierTypeViewHolder(
        private val binding: ListItemCarrierTypeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CarrierType) {
            binding.apply {
                carrierType = item
                setClickListener { view ->
                    clickType(item)
                }
                executePendingBindings()
            }
        }

        // todo:
        //   1. di 에서 fragment 인지, activity 잘 상속받고 있는것인지
        //   2. lifecycleowner livedata를 직접 연결하는거랑 binding.lifecycleowner 랑 무슨 차이인지 확인
        //   3.(O) 대형 소형 중형을 직접 데이터로 넣으려고 하는데 어떻게 할것인지
        //     대형 소형 중형 데이터를 입력해야한다. --> ViewModel 을 통해서 삽입하였음
    }

}

class CarrierTypeDiffCallback : DiffUtil.ItemCallback<CarrierType>() {

    override fun areItemsTheSame(oldItem: CarrierType, newItem: CarrierType): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: CarrierType, newItem: CarrierType): Boolean {
        return oldItem.type == newItem.type
    }
}