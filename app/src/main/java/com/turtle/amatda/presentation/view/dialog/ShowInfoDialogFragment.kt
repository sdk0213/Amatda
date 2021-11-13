package com.turtle.amatda.presentation.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentDialogShowInfoBinding
import com.turtle.amatda.presentation.view.base.BaseDialogFragment

// Navigation Component 를 사용하여서 호출하지 말고 FragmentManager 를 통한 직접 호출
class ShowInfoDialogFragment constructor(
    @LayoutRes private val layoutId: Int // 붙힐 Layout Id
) : BaseDialogFragment<FragmentDialogShowInfoBinding>(R.layout.fragment_dialog_show_info) {

    override fun initViewCreated(inflater: LayoutInflater) {}

    override fun init() {
        view()
        listener()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val fragmentTransaction = manager.beginTransaction()
        fragmentTransaction.add(this, tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun view() {
        val layoutInflater =
            (mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        layoutInflater.inflate(layoutId, binding.dialogFragmentShowInfoContainer)
    }

    private fun listener() {
        binding.btnShowInfoOk.setOnClickListener {
            dialog?.dismiss()
        }
    }

}