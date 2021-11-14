package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.UploadFile
import com.turtle.amatda.domain.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun updateUser(user: User): Completable
    fun getUser(user: User): Observable<User>
    fun updateUserFile(uploadFile: UploadFile): Single<String>
}