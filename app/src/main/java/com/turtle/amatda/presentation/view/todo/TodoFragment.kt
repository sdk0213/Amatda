package com.turtle.amatda.presentation.view.todo

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.turtle.amatda.databinding.FragmentTodoBinding
import com.turtle.amatda.presentation.view.base.BaseFragment
import com.turtle.amatda.presentation.view.home.HomeViewPagerFragmentDirections
import dagger.Lazy
import javax.inject.Inject

class TodoFragment : BaseFragment<TodoViewModel, FragmentTodoBinding>() {

    private lateinit var todoAdapter: TodoAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var navController: Lazy<NavController>

    override fun init() {
        todoAdapter = TodoAdapter()
        binding.todoRecyclerView.adapter = todoAdapter
        binding.todoRecyclerView.layoutManager = linearLayoutManager
        binding.setClickListener {
            findNavController().navigate(HomeViewPagerFragmentDirections.actionViewFragmentToViewItemselectFragment())
        }

        subscribeTodoFromDb()
    }

    fun subscribeTodoFromDb(){
        viewModel.getTodo().observe(viewLifecycleOwner){
            todoAdapter.submitList(it)
        }
    }
}