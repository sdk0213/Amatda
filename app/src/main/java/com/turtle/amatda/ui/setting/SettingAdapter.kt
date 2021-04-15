package com.turtle.amatda.ui.setting

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.ui.todo.PlantDiffCallback
import com.turtle.amatda.data.Todo
import javax.inject.Inject

class SettingAdapter @Inject constructor() : ListAdapter<Todo, RecyclerView.ViewHolder>(
    PlantDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}