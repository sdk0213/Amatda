package com.turtle.amatda.data.api

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.turtle.amatda.data.model.FirestoreEntity
import com.turtle.amatda.data.model.FirestoreUser
import com.turtle.amatda.data.model.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FirebaseFirestoreApiService @Inject constructor(
    private val fireStore: FirebaseFirestore
) {

    fun updateUser(firestoreEntity: FirestoreEntity): Completable {
        return Completable.create { emitter ->
            fireStore.collection(firestoreEntity.collection.collectionName)
                .document(firestoreEntity.document)
                .set(firestoreEntity.field)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }

        }
    }

    fun getUser(firestoreEntity: FirestoreEntity): Observable<UserEntity> {
        return Observable.create { emitter ->
            fireStore.collection(firestoreEntity.collection.collectionName)
                .document(firestoreEntity.document)
                .addSnapshotListener { value, error ->
                    error?.let {
                        emitter.onError(error)
                        return@addSnapshotListener
                    }

                    value?.let { user ->
                        if (user.exists()) {
                            user.data?.let {
                                emitter.onNext(
                                    UserEntity(
                                        id = firestoreEntity.document,
                                        email = it[FirestoreUser.EMAIL.variable] as String,
                                        password = it[FirestoreUser.PASSWORD.variable] as String,
                                        nickName = it[FirestoreUser.NICKNAME.variable] as String,
                                        photo = it[FirestoreUser.PHOTO.variable] as String,
                                        exp = it[FirestoreUser.EXP.variable] as Long
                                    )
                                )
                            }
                        }
                    }

                }
        }
    }

}