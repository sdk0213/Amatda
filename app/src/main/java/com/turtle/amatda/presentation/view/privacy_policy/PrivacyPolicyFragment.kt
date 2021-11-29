package com.turtle.amatda.presentation.view.privacy_policy

import android.annotation.SuppressLint
import android.webkit.WebViewClient
import androidx.navigation.findNavController
import com.turtle.amatda.R
import com.turtle.amatda.data.api.ApiClient
import com.turtle.amatda.databinding.FragmentPrivacyPolicyBinding
import com.turtle.amatda.presentation.view.base.BaseFragment


class PrivacyPolicyFragment :
    BaseFragment<PrivacyPolicyViewModel, FragmentPrivacyPolicyBinding>(R.layout.fragment_privacy_policy) {

    override fun init() {
        view()
        initAdapter()
        viewModel()
        listener()
        observer()
    }

    @SuppressLint("SetJavaScriptEnabled") // javascript 허용하면 위험하니까 조심하라는 경고
    private fun view() {
        binding.topAppBar.apply {
            setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24)
            setNavigationIconTint(0xFFFFFFFF.toInt())
            setOnClickListener {
                findNavController().navigateUp()
            }
        }

        binding.webviewPrivacyPolicy.webViewClient = WebViewClient()
        binding.webviewPrivacyPolicy.settings.javaScriptEnabled = true
        binding.webviewPrivacyPolicy.settings.domStorageEnabled = true
        binding.webviewPrivacyPolicy.loadUrl(ApiClient.PRIVACY_POLICY_URL)
    }

    private fun initAdapter() {
    }

    private fun viewModel() {
        binding.viewModel = viewModel
    }

    private fun listener() {
    }

    private fun observer() {
    }

}