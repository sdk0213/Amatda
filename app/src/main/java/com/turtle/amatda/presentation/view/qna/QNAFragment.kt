package com.turtle.amatda.presentation.view.qna

import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentQnaBinding
import com.turtle.amatda.domain.model.QnaData
import com.turtle.amatda.presentation.view.base.BaseFragment


class QNAFragment : BaseFragment<QNAViewModel, FragmentQnaBinding>(R.layout.fragment_qna) {

    private lateinit var qnaAdapter: QNAAdapter

    override fun init() {
        view()
        initAdapter()
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

    private fun initAdapter() {
        qnaAdapter = QNAAdapter()
        binding.recyclerviewQna.adapter = qnaAdapter
        val mutableQnaDataList = mutableListOf<QnaData>()
        val question = resources.getStringArray(R.array.question)
        val answer = resources.getStringArray(R.array.answer)

        for (number in question.indices) {
            mutableQnaDataList.add(
                QnaData(
                    question = question[number],
                    answer = answer[number]
                )
            )
        }
        qnaAdapter.submitList(mutableQnaDataList)
    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }

    private fun listener() {
    }

    private fun observer() {
    }

}