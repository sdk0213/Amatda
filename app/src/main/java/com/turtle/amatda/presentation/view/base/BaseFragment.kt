package com.turtle.amatda.presentation.view.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.turtle.amatda.presentation.di.AppViewModelFactory
import dagger.android.support.DaggerFragment
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject


abstract class BaseFragment<T : BaseViewModel, B : ViewDataBinding> : DaggerFragment() {

    companion object {
        val TAG : String= BaseFragment::class.java.simpleName
    }

    private lateinit var mContext: Context

    @Inject
    protected lateinit var binding: B

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    protected val viewModel: T
        get(){
            val types: Array<Type> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
            return ViewModelProvider(this, viewModelFactory).get(types[0] as Class<T>)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mContext = inflater.context
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        init()
    }

    abstract fun init()

    protected fun showToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}