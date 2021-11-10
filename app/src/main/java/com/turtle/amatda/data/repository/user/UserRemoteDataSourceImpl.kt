package com.turtle.amatda.data.repository.user

import com.turtle.amatda.data.api.FirebaseFirestoreApiService
import com.turtle.amatda.data.model.AmatdaCollection
import com.turtle.amatda.data.model.FirestoreEntity
import com.turtle.amatda.data.model.FirestoreUser
import com.turtle.amatda.data.model.UserEntity
import com.turtle.amatda.domain.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val firebaseFirestoreApiService: FirebaseFirestoreApiService
) : UserRemoteDataSource {

    override fun updateUser(userEntity: UserEntity): Completable {
        return firebaseFirestoreApiService.updateUser(
            FirestoreEntity(
                collection = AmatdaCollection.USER,
                document = userEntity.id,
                field = hashMapOf(
                    FirestoreUser.EMAIL.variable to userEntity.email,
                    FirestoreUser.PASSWORD.variable to userEntity.password,
                    FirestoreUser.NICKNAME.variable to userEntity.nickName,
                    FirestoreUser.PHOTO.variable to userEntity.photo,
                    FirestoreUser.EXP.variable to userEntity.exp
                )
            )
        )
    }

    override fun getUser(userEntity: UserEntity): Observable<UserEntity> {
        return firebaseFirestoreApiService.getUser(
            FirestoreEntity(
                collection = AmatdaCollection.USER,
                document = userEntity.id
            )
        )
    }

}