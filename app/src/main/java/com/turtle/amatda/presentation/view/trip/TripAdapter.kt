//package com.turtle.amatda.presentation.view.trip
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.turtle.amatda.databinding.ListItemTodoBinding
//import com.turtle.amatda.databinding.ListItemTripBinding
//import com.turtle.amatda.domain.model.Todo
//
//class TripAdapter : ListAdapter<Todo, TripAdapter.TripViewHolder>(
//    TripDiffCallback()
//) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
//        return TripViewHolder(
//            ListItemTripBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    class TripViewHolder(
//        private val binding: ListItemTripBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: Todo) {
//            binding.apply {
//                todo = item
//                executePendingBindings()
//            }
//        }
//    }
//
//}
//
//class TripDiffCallback : DiffUtil.ItemCallback<Todo>() {
//
//    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
//        return oldItem == newItem
//    }
//}