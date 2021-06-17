package com.turtle.amatda.presentation.view.date

import com.turtle.amatda.databinding.FragmentDateBinding
import com.turtle.amatda.presentation.view.base.BaseFragment

class DateFragment : BaseFragment<DateViewModel, FragmentDateBinding>() {

    private lateinit var dateAdapter: DateAdapter

    override fun init() {
        initAdapter()
    }

    private fun initAdapter(){
        dateAdapter = DateAdapter()
    }

}