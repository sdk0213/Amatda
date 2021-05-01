package com.turtle.amatda.data.repository.todo

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.todo.TodoEntity
import com.turtle.amatda.domain.model.Todo
import com.turtle.amatda.domain.repository.TodoRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val mapper: Mapper<TodoEntity, Todo>,
    private val factory: TodoDataSourceFactory
) : TodoRepository {

    override fun getUserTodoAll(): Flowable<List<Todo>> {
        return factory.getTodoAll().map { list ->
            list.map { todo ->
                mapper.mapFromEntity(todo)
            }
        }
    }

    override fun insertTodo(todo: Todo): Completable {
        val todoEntity = mapper.mapToEntity(todo)
        return factory.insertTodo(todoEntity)
    }


}