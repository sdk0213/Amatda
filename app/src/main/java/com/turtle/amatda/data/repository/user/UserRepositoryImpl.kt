package com.turtle.amatda.data.repository.user

import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.UserEntity
import com.turtle.amatda.domain.model.UploadFile
import com.turtle.amatda.domain.model.User
import com.turtle.amatda.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mapper: Mapper<UserEntity, User>,
    private val factory: UserDataSourceFactory
) : UserRepository {

    override fun updateUser(user: User): Completable {
        val entity = mapper.mapToEntity(user)
        return factory.updateUser(entity)
    }

    override fun getUser(user: User): Observable<User> {
        val entity = mapper.mapToEntity(user)
        return factory.getUser(entity)
            .map {
                mapper.entityToMap(it)
            }
    }

    override fun updateUserFile(uploadFile: UploadFile): Single<String> {
        return factory.updateUserFile(uploadFile)
    }

}