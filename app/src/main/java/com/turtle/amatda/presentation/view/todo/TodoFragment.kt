package com.turtle.amatda.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.turtle.amatda.data.model.todo.TodoEntity
import com.turtle.amatda.databinding.FragmentTodoBinding
import com.turtle.amatda.presentation.di.AppViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TodoFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    @Inject
    lateinit var binding: FragmentTodoBinding

    @Inject
    lateinit var todoAdapter: TodoAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    val todoViewModel: TodoViewModel by viewModels{
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = todoViewModel
        binding.todoRecyclerView.adapter = todoAdapter
        binding.todoRecyclerView.layoutManager = linearLayoutManager

        subscribeTodoFromDb()
    }

    fun subscribeTodoFromDb(){
        todoViewModel.getTodo().observe(viewLifecycleOwner){
            todoAdapter.submitList(it)
        }
    }


    fun interface Callback {
        fun add(todo: TodoEntity?)
    }
}