package com.turtle.amatda.presentation.view.mypage

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentMypageBinding
import com.turtle.amatda.domain.model.Level
import com.turtle.amatda.presentation.android.view_data.EditTextData
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.utilities.extensions.getNavigationResult
import com.turtle.amatda.presentation.view.base.BaseFragment
import com.turtle.amatda.presentation.view.dialog.ShowInfoDialogFragment
import java.io.*
import javax.inject.Inject


class MyPageFragment :
    BaseFragment<MyPageViewModel, FragmentMypageBinding>(R.layout.fragment_mypage) {

    @Inject
    lateinit var firebaseStorage: FirebaseStorage

    private val resultActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == Activity.RESULT_OK) {
//                Timber.d("user selected image ${result.data}")
                result.data?.data?.let { uri ->
                    viewModel.uploadProfileImage(uri)
                }
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
                Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "image/*"
                    flags =
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                }, "Get Album")
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
            binding.tvMyPageMyTier.text = "(${level.rank})"
            val myExp = user.exp - level.exp
            val upExp = level.upExp
            val percentExp = (myExp.toDouble() / upExp.toDouble() * 100).toInt()
            binding.tvMyPageMyExp.text = "$percentExp% ($myExp / $upExp)"
            binding.progressMyPageMyExp.progress = percentExp
            Glide.with(mContext)
                .load(user.photo)
                .placeholder(R.drawable.external_image_loading)
                .into(binding.imageViewMyPageProfile)
            when (level.level) {
                1L -> binding.imageViewMyPageTier.setImageResource(R.drawable.ic_icon_level_1)
                2L -> binding.imageViewMyPageTier.setImageResource(R.drawable.ic_icon_level_2)
                3L -> binding.imageViewMyPageTier.setImageResource(R.drawable.ic_icon_level_3)
                4L -> binding.imageViewMyPageTier.setImageResource(R.drawable.ic_icon_level_4)
                5L -> binding.imageViewMyPageTier.setImageResource(R.drawable.ic_icon_level_5)
                6L -> binding.imageViewMyPageTier.setImageResource(R.drawable.ic_icon_level_6)
                7L -> binding.imageViewMyPageTier.setImageResource(R.drawable.ic_icon_level_7)
                8L -> binding.imageViewMyPageTier.setImageResource(R.drawable.ic_icon_level_8)
            }
            binding.tvMyPageMyLevel.text = level.level.toString()
        }

        viewModel.updateUser.observe(this@MyPageFragment, EventObserver { isUpdate ->
            if (isUpdate) {
                showToast(getString(R.string.toast_message_my_page_update_user))
            }
        })

        viewModel.updateDB.observe(this@MyPageFragment, EventObserver { isDbUpdate ->
            if (isDbUpdate) {
                showToast("서버에 업데이트 되었습니다.")
            }
        })

        viewModel.restoreDB.observe(this@MyPageFragment, EventObserver { isResotred ->
            if (isResotred) {
                showToast("복원 완료하였습니다.")
            }
        })
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
        } else {
            Level.LEVEL_8
        }
    }

}