package com.turtle.amatda.data.repository.user

import com.turtle.amatda.data.model.UserEntity
import com.turtle.amatda.domain.model.UploadFile
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
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

    fun updateUserFile(uploadFile: UploadFile): Single<String> {
        return remoteDataSource.updateUserFile(uploadFile)
    }
}