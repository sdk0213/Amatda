package com.turtle.amatda.presentation.view.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.turtle.amatda.data.model.todo.TodoEntity
import com.turtle.amatda.domain.model.Todo
import com.turtle.amatda.domain.repository.TodoRepository
import com.turtle.amatda.domain.usecases.AddTodoUseCase
import com.turtle.amatda.domain.usecases.GetTodoUseCase
import javax.inject.Inject
import javax.inject.Named

class TodoViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase,
    private val getTodoUseCase: GetTodoUseCase
    ) : ViewModel() {

    fun getTodo(): LiveData<List<Todo>> {
        return getTodoUseCase.getTodoAll().toLiveData()
    }

    fun insertTodo() {
        addTodoUseCase.insertTodo()
    }


}
