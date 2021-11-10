package com.turtle.amatda.data.repository.user

import com.turtle.amatda.data.model.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRemoteDataSource {
    fun updateUser(userEntity: UserEntity): Completable
    fun getUser(userEntity: UserEntity): Observable<UserEntity>
}