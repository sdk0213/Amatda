package com.turtle.amatda.presentation.view.dialog

import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentDialogYesOrNoBinding
import com.turtle.amatda.presentation.utilities.extensions.setNavigationResult
import com.turtle.amatda.presentation.view.base.BaseDialogFragment

class YesOrNoDialogFragment :
    BaseDialogFragment<FragmentDialogYesOrNoBinding>(R.layout.fragment_dialog_yes_or_no) {

    private val args: TextViewDialogFragmentArgs by navArgs()

    override fun initViewCreated(inflater: LayoutInflater) {}

    override fun init() {
        initArgs()
        listener()
    }

    private fun initArgs() {
        args?.let { args ->
            returnKey = args.textViewData.returnKey
            binding.tvDialogShowYesOrNo.text = args.textViewData.text
        }
    }

    private fun listener() {

        binding.btnDialogShowYesOrNoCancel.setOnClickListener {
            setNavigationResult(returnKey, DIALOG_RETURN_VALUE_CANCEL)
            findNavController().navigateUp()
        }

        binding.btnDialogShowYesOrNoOk.setOnClickListener {
            setNavigationResult(returnKey, DIALOG_RETURN_VALUE_OK)
            findNavController().navigateUp()
        }
    }
}