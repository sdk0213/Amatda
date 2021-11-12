package com.turtle.amatda.presentation.view.mypage

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentMypageBinding
import com.turtle.amatda.domain.model.Level
import com.turtle.amatda.presentation.android.view_data.EditTextData
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.utilities.extensions.getNavigationResult
import com.turtle.amatda.presentation.view.base.BaseFragment
import com.turtle.amatda.presentation.view.dialog.ShowInfoDialogFragment
import timber.log.Timber

class MyPageFragment :
    BaseFragment<MyPageViewModel, FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val resultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == Activity.RESULT_OK) {
                Timber.d("user selected image ${result.data}")
                viewModel.saveProfileImage(result.data as Any)
            } else if (result?.resultCode == Activity.RESULT_CANCELED) {
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

        binding.btnMyPageEditNickname.setOnClickListener {
            findNavController().navigate(
                MyPageFragmentDirections.actionGlobalEditTextDialog(
                    EditTextData(
                        title = getString(R.string.dialog_edit_text_nickname_title),
                        hint = getString(R.string.dialog_edit_text_nickname_hint),
                        text = viewModel.currentUser.value?.nickName ?: "알수 없음"
                    )
                )
            )
        }

        binding.btnMyPageInfoLevel.setOnClickListener {
            ShowInfoDialogFragment(R.layout.fragment_dialog_level).show(childFragmentManager, tag)
        }
    }

    private fun observer() {

        getNavigationResult<String>(
            id = R.id.view_fragment_main,
            key = DIALOG_RETURN_KEY,
            onResult = { nickName ->
                Timber.d("닉네임 변경 : $nickName")
                viewModel.editNickName(nickName)
            })

        viewModel.logout.observe(this@MyPageFragment, EventObserver { isLogout ->
            if (isLogout) {
                findNavController().navigate(
                    MyPageFragmentDirections.actionGlobalIntroFragment()
                )
            }
        })

        viewModel.currentUser.observe(this@MyPageFragment) { user ->
            val level = getLevel(user.exp)
            binding.tvMyPageMyLevel.text = "${getString(R.string.my_page_level)}${level.rank}"
            val myExp = user.exp - level.exp
            val upExp = level.upExp
            val percentExp = (myExp.toDouble() / upExp.toDouble() * 100).toInt()
            binding.tvMyPageMyExp.text = "$percentExp% ($myExp / $upExp)"
            binding.progressMyPageMyExp.progress = percentExp
        }
    }

    private fun getLevel(exp: Long): Level {
        return if (exp >= Level.LEVEL_1.exp && exp < Level.LEVEL_2.exp) {
            Level.LEVEL_1
        } else if (exp >= Level.LEVEL_2.exp && exp < Level.LEVEL_3.exp) {
            Level.LEVEL_2
        } else if (exp >= Level.LEVEL_3.exp && exp < Level.LEVEL_4.exp) {
            Level.LEVEL_3
        } else if (exp >= Level.LEVEL_4.exp && exp < Level.LEVEL_5.exp) {
            Level.LEVEL_4
        } else if (exp >= Level.LEVEL_5.exp && exp < Level.LEVEL_6.exp) {
            Level.LEVEL_5
        } else if (exp >= Level.LEVEL_6.exp && exp < Level.LEVEL_7.exp) {
            Level.LEVEL_6
        } else if (exp >= Level.LEVEL_7.exp && exp < Level.LEVEL_8.exp) {
            Level.LEVEL_7
        } else if (exp >= Level.LEVEL_8.exp && exp < Level.LEVEL_9.exp) {
            Level.LEVEL_8
        } else if (exp >= Level.LEVEL_9.exp && exp < Level.LEVEL_10.exp) {
            Level.LEVEL_9
        } else if (exp >= Level.LEVEL_10.exp && exp < Level.LEVEL_99.exp) {
            Level.LEVEL_10
        } else {
            Level.LEVEL_99
        }
    }

}