package com.turtle.amatda.presentation.view.qna

import androidx.navigation.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentQnaBinding
import com.turtle.amatda.presentation.view.base.BaseFragment


class QNAFragment : BaseFragment<QNAViewModel, FragmentQnaBinding>(R.layout.fragment_qna) {

    override fun init() {
        view()
        viewModel()
        listener()
        observer()
    }


    private fun view() {

        binding.topAppBar.apply {
            setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24)
            setNavigationIconTint(0xFFFFFFFF.toInt())
            setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }

    private fun listener() {
    }

    private fun observer() {
    }

}