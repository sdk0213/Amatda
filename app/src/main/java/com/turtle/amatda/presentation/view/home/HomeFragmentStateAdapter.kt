package com.turtle.amatda.presentation.view.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.turtle.amatda.presentation.view.date.DateFragment
import com.turtle.amatda.presentation.view.setting.SettingFragment
import com.turtle.amatda.presentation.view.todo.TodoFragment

class HomeFragmentStateAdapter constructor(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TodoFragment()
            1 -> DateFragment()
            else -> SettingFragment()
        }
    }
}