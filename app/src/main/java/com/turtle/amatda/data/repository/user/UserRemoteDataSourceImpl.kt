package com.turtle.amatda.data.repository.user

import com.turtle.amatda.data.api.FirebaseFirestoreApiService
import com.turtle.amatda.data.api.FirebaseStorageApiService
import com.turtle.amatda.data.model.AmatdaReference
import com.turtle.amatda.data.model.FirebaseStorageEntity
import com.turtle.amatda.data.model.UserEntity
import com.turtle.amatda.domain.model.Experience
import com.turtle.amatda.domain.model.UploadFile
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val firebaseFirestoreApiService: FirebaseFirestoreApiService,
    private val firebaseFirebaseStorageApiService: FirebaseStorageApiService
) : UserRemoteDataSource {

    override fun updateUser(userEntity: UserEntity): Completable {
        return firebaseFirestoreApiService.updateUser(userEntity)
    }

    override fun getUser(userEntity: UserEntity): Observable<UserEntity> {
        return firebaseFirestoreApiService.getUser(userEntity)
    }

    override fun updateUserFile(uploadFile: UploadFile): Single<String> {
        return firebaseFirebaseStorageApiService.uploadLocalFile(
            FirebaseStorageEntity(
                ref = AmatdaReference.USERS,
                fileName = uploadFile.fileName,
                fileUri = uploadFile.fileUri
            )
        )
    }

    override fun updateUserExp(experience: Experience): Completable {
        return firebaseFirestoreApiService.updateUserExp(experience)
    }
}