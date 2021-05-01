package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.todo.TodoEntity
import com.turtle.amatda.domain.model.Todo
import javax.inject.Inject

open class TodoMapper @Inject constructor(): Mapper<TodoEntity, Todo> {

    override fun mapFromEntity(type: TodoEntity): Todo {
        return Todo(type.Id, type.title, type.subTitle)
    }

    override fun mapToEntity(type: Todo): TodoEntity {
        return TodoEntity(type.Id, type.title, type.subTitle)
    }

}