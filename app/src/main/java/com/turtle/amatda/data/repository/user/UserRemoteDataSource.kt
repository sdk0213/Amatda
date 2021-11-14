package com.turtle.amatda.data.repository.user

import com.turtle.amatda.data.model.UserEntity
import com.turtle.amatda.domain.model.UploadFile
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserRemoteDataSource {
    fun updateUser(userEntity: UserEntity): Completable
    fun getUser(userEntity: UserEntity): Observable<UserEntity>
    fun updateUserFile(uploadFile: UploadFile): Single<String>
}