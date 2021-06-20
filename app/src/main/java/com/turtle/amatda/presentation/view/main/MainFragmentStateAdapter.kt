package com.turtle.amatda.presentation.view.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.turtle.amatda.presentation.view.carrier.CarrierFragment
import com.turtle.amatda.presentation.view.home.HomeFragment
import com.turtle.amatda.presentation.view.mypage.MyPageFragment
import com.turtle.amatda.presentation.view.trip.TripFragment

class MainFragmentStateAdapter constructor(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> CarrierFragment()
            2 -> TripFragment()
            else -> MyPageFragment()
        }
    }
}