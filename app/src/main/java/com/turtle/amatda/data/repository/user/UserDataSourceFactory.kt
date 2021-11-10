package com.turtle.amatda.data.repository.user

import com.turtle.amatda.data.model.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserDataSourceFactory @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) {

    fun updateUser(userEntity: UserEntity): Completable {
        return remoteDataSource.updateUser(userEntity)
    }

    fun getUser(userEntity: UserEntity): Observable<UserEntity> {
        return remoteDataSource.getUser(userEntity)
    }
}