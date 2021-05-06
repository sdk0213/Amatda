package com.turtle.amatda.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.turtle.amatda.R
import com.turtle.amatda.data.model.todo.TodoEntity
import com.turtle.amatda.databinding.FragmentTodoBinding
import com.turtle.amatda.presentation.di.AppViewModelFactory
import dagger.Lazy
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

    @Inject
    lateinit var navController: Lazy<NavController>

    val viewModel: TodoViewModel by viewModels{
        viewModelFactory
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
        binding.viewModel = viewModel
        binding.todoRecyclerView.adapter = todoAdapter
        binding.todoRecyclerView.layoutManager = linearLayoutManager
        binding.setClickListener {
            viewModel.insertTodo() // TODO: 5/6/21 Test Code 반드시 삭제 필요 
            findNavController().navigate(R.id.action_view_fragment_to_view_itemselect_fragment)
        }

        subscribeTodoFromDb()
    }

    fun subscribeTodoFromDb(){
        viewModel.getTodo().observe(viewLifecycleOwner){
            todoAdapter.submitList(it)
        }
    }


    fun interface Callback {
        fun add(todo: TodoEntity?)
    }
}