package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.UserEntity
import com.turtle.amatda.domain.model.User
import javax.inject.Inject

open class UserMapper @Inject constructor() : Mapper<UserEntity, User> {
    override fun entityToMap(type: UserEntity): User {
        return User(
            id = type.id,
            email = type.email,
            password = type.password,
            nickName = type.nickName,
            photo = type.photo,
            exp = type.exp
        )
    }

    override fun mapToEntity(type: User): UserEntity {
        return UserEntity(
            id = type.id,
            email = type.email,
            password = type.password,
            nickName = type.nickName,
            photo = type.photo,
            exp = type.exp
        )
    }


}