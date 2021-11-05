package com.turtle.amatda.presentation.view.mypage

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentMypageBinding
import com.turtle.amatda.presentation.view.base.BaseFragment
import timber.log.Timber

class MyPageFragment :
    BaseFragment<MyPageViewModel, FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val resultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == Activity.RESULT_OK) {
                Timber.d("user selected image ${result.data}")
                viewModel.saveProfileImage(result.data as Any)
            } else if(result?.resultCode == Activity.RESULT_CANCELED){
                showToast("사진 선택을 취소하였습니다.")
            }
        }

    override fun init() {
        view()
        viewModel()
        listener()
        observer()
    }

    private fun view() {

    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }

    private fun listener() {
        binding.buttonMyPageAddMyProfileImage.setOnClickListener {
            resultActivity.launch(
                Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
            )
        }
    }

    private fun observer() {

    }

}