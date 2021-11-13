package com.turtle.amatda.presentation.view.dialog

import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentDialogTextViewBinding
import com.turtle.amatda.presentation.view.base.BaseDialogFragment

class TextViewDialogFragment :
    BaseDialogFragment<FragmentDialogTextViewBinding>(R.layout.fragment_dialog_text_view) {

    private val args: TextViewDialogFragmentArgs by navArgs()

    override fun initViewCreated(inflater: LayoutInflater) {}

    override fun init() {
        initArgs()
        listener()
    }

    private fun initArgs() {
        args?.let { args ->
            binding.tvDialogShowTextView.text = args.textViewData.text
        }
    }

    private fun listener() {

        binding.btnDialogShowTextViewOk.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}