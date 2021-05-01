package com.turtle.amatda.data.repository.todo

import com.turtle.amatda.data.model.todo.ItemEntity
import com.turtle.amatda.data.model.todo.TodoEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TodoDataSourceFactory @Inject constructor(
    private val localDataSource: TodoLocalDataSource
) {
    fun getTodoAll() : Flowable<List<TodoEntity>> {
        return localDataSource.getTodoAll()
    }

    fun insertTodo(todoEntity: TodoEntity) : Completable {
        return localDataSource.insertTodo(todoEntity)
    }
}