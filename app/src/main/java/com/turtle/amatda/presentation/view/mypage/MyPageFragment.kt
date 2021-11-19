package com.turtle.amatda.presentation.view.mypage

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.storage.FirebaseStorage
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentMypageBinding
import com.turtle.amatda.domain.model.Level
import com.turtle.amatda.presentation.android.view_data.EditTextData
import com.turtle.amatda.presentation.android.view_data.TextViewData
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.utilities.extensions.getNavigationResult
import com.turtle.amatda.presentation.utilities.extensions.isConnected
import com.turtle.amatda.presentation.view.base.BaseFragment
import com.turtle.amatda.presentation.view.dialog.ShowInfoDialogFragment
import java.io.*
import javax.inject.Inject


class MyPageFragment :
    BaseFragment<MyPageViewModel, FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val returnKeyDialogRename = "dialogReturnKeyRename"
    private val returnKeyDialogImport = "dialogReturnKeyImport"
    private val returnKeyDialogExport = "dialogReturnKeyExport"
    private val returnKeyDialogLogout = "returnKeyDialogLogout"

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
            if (!mContext.isConnected()) {
                showPopUpMessage(getString(R.string.common_message_required_internet_connection))
                return@setOnClickListener
            }
            findNavController().navigate(
                MyPageFragmentDirections.actionGlobalEditTextDialog(
                    EditTextData(
                        returnKey = returnKeyDialogRename,
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

        binding.buttonMyPageImportDbServer.setOnClickListener {
            if (!mContext.isConnected()) {
                showPopUpMessage(getString(R.string.common_message_required_internet_connection))
                return@setOnClickListener
            }
            findNavController().navigate(
                MyPageFragmentDirections.actionGlobalShowYesOrNoDialog(
                    TextViewData(
                        returnKey = returnKeyDialogImport,
                        text = getString(R.string.dialog_message_my_page_import_db)
                    )
                )
            )
        }

        binding.buttonMyPageExportDbServer.setOnClickListener {
            if (!mContext.isConnected()) {
                showPopUpMessage(getString(R.string.common_message_required_internet_connection))
                return@setOnClickListener
            }
            findNavController().navigate(
                MyPageFragmentDirections.actionGlobalShowYesOrNoDialog(
                    TextViewData(
                        returnKey = returnKeyDialogExport,
                        text = getString(R.string.dialog_message_my_page_export_db)
                    )
                )
            )
        }

        binding.buttonMyPageLogout.setOnClickListener {
            if (!mContext.isConnected()) {
                showPopUpMessage(getString(R.string.common_message_required_internet_connection))
                return@setOnClickListener
            }
            findNavController().navigate(
                MyPageFragmentDirections.actionGlobalShowYesOrNoDialog(
                    TextViewData(
                        returnKey = returnKeyDialogLogout,
                        text = getString(R.string.dialog_message_my_page_logout)
                    )
                )
            )
        }

        binding.buttonMyPageOpenSourceLicense.setOnClickListener {
            OssLicensesMenuActivity.setActivityTitle("오픈소스 라이선스")
            startActivity(
                Intent(
                    mContext,
                    OssLicensesMenuActivity::class.java
                )
            ) // ActionBar의 title 변경
        }

        binding.buttonMyPageQna.setOnClickListener {
            findNavController().navigate(
                MyPageFragmentDirections.actionGlobalQnaFragment()
            )
        }
    }

    private fun observer() {

        getNavigationResult<String>(
            id = R.id.view_fragment_main,
            key = returnKeyDialogRename,
            onResult = { nickName ->
                viewModel.editNickName(nickName)
            })

        getNavigationResult<String>(
            id = R.id.view_fragment_main,
            key = returnKeyDialogImport,
            onResult = { RETURN ->
                when (RETURN) {
                    DIALOG_RETURN_VALUE_OK -> {
                        viewModel.importCarrierDataToDbUseCase()
                    }
                    DIALOG_RETURN_VALUE_CANCEL -> {
                        showToast(getString(R.string.toast_message_my_page_cancel_import_db))
                    }
                }
            })

        getNavigationResult<String>(
            id = R.id.view_fragment_main,
            key = returnKeyDialogExport,
            onResult = { RETURN ->
                when (RETURN) {
                    DIALOG_RETURN_VALUE_OK -> {
                        viewModel.exportCarrierDataToDbUseCase()
                    }
                    DIALOG_RETURN_VALUE_CANCEL -> {
                        showToast(getString(R.string.toast_message_my_page_cancel_export_db))
                    }
                }
            })

        getNavigationResult<String>(
            id = R.id.view_fragment_main,
            key = returnKeyDialogLogout,
            onResult = { RETURN ->
                when (RETURN) {
                    DIALOG_RETURN_VALUE_OK -> {
                        viewModel.logout()
                    }
                }
            })

        viewModel.logout.observe(this@MyPageFragment, EventObserver { isLogout ->
            if (isLogout) {
                findNavController().navigate(
                    MyPageFragmentDirections.actionGlobalIntroFragment()
                )
            } else {
                showToast(getString(R.string.toast_message_my_page_logout_failed))
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
                1L -> binding.imageViewMyPageTier.setImageResource(R.drawable.flaticon_com_ic_level_1)
                2L -> binding.imageViewMyPageTier.setImageResource(R.drawable.flaticon_com_ic_level_2)
                3L -> binding.imageViewMyPageTier.setImageResource(R.drawable.flaticon_com_ic_level_3)
                4L -> binding.imageViewMyPageTier.setImageResource(R.drawable.flaticon_com_ic_level_4)
                5L -> binding.imageViewMyPageTier.setImageResource(R.drawable.flaticon_com_ic_level_5)
                6L -> binding.imageViewMyPageTier.setImageResource(R.drawable.flaticon_com_ic_level_6)
                7L -> binding.imageViewMyPageTier.setImageResource(R.drawable.flaticon_com_ic_level_7)
                8L -> binding.imageViewMyPageTier.setImageResource(R.drawable.flaticon_com_ic_level_8)
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
                showToast(getString(R.string.toast_message_my_page_ok_export_db))
            }
        })

        viewModel.restoreDB.observe(this@MyPageFragment, EventObserver { isResotred ->
            if (isResotred) {
                showToast(getString(R.string.toast_message_my_page_ok_import_db))
            }
        })

        viewModel.noDataInServerDB.observe(this@MyPageFragment, EventObserver { noData ->
            if (noData) {
                showToast(getString(R.string.toast_message_my_page_failed_import_db_no_data))
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