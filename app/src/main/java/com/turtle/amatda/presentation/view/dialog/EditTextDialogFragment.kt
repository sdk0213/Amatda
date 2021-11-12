package com.turtle.amatda.presentation.view.dialog

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentEditTextDialogBinding
import com.turtle.amatda.presentation.utilities.extensions.setNavigationResult
import com.turtle.amatda.presentation.utilities.extensions.toEditable
import com.turtle.amatda.presentation.view.base.BaseDialogFragment

class EditTextDialogFragment :
    BaseDialogFragment<FragmentEditTextDialogBinding>(R.layout.fragment_edit_text_dialog) {

    private val args: EditTextDialogFragmentArgs by navArgs()

    override fun initViewCreated() {
//        // 타이틀 제거
//        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        // 백그라운드 컬러 투명하게
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        // 다어어로그 캔슬 불가
//        isCancelable = false
    }

    override fun init() {
        initArgs()
        listener()
    }

    private fun initArgs() {
        args?.let { args ->
            binding.tvEditTextDialogGuide.text = args.editTextData?.title
            binding.tilEditTextDialog.editText?.text = args.editTextData?.text?.toEditable()
            binding.tilEditTextDialog.editText?.hint = args.editTextData?.hint
        }
    }

    private fun listener() {

        binding.btnEditTextDialogOk.setOnClickListener {
            setNavigationResult(DIALOG_RETURN_KEY, binding.tieEditTextDialog.text.toString())
            findNavController().navigateUp()
        }

        binding.btnEditTextDialogCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}