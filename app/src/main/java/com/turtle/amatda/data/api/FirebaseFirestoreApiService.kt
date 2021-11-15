package com.turtle.amatda.data.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.turtle.amatda.data.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class FirebaseFirestoreApiService @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    fun updateUser(userEntity: UserEntity): Completable {
        return Completable.create { emitter ->
            firebaseAuth.uid?.let { uid ->
                fireStore.collection(FirestoreCollection.USER.collectionName)
                    .document(uid)
                    .set(
                        hashMapOf(
                            FirestoreUser.EMAIL.variable to userEntity.email,
                            FirestoreUser.PASSWORD.variable to userEntity.password,
                            FirestoreUser.NICKNAME.variable to userEntity.nickName,
                            FirestoreUser.PHOTO.variable to userEntity.photo,
                            FirestoreUser.EXP.variable to userEntity.exp
                        )
                    )
                    .addOnSuccessListener {
                        emitter.onComplete()
                    }
                    .addOnFailureListener {
                        emitter.onError(it)
                    }
            } ?: run {
                emitter.onError(Exception("non-exist user"))
            }

        }
    }

    fun getUser(userEntity: UserEntity): Observable<UserEntity> {
        return Observable.create { emitter ->
            firebaseAuth.uid?.let { uid ->
                fireStore.collection(FirestoreCollection.USER.collectionName)
                    .document(uid)
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
                                            id = uid,
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
            } ?: run {
                emitter.onError(Exception("non-exist user"))

            }
        }
    }

    fun exportCarrier(
        carrierData: CarrierData
    ): Completable {
        return Completable.create { emitter ->
            fireStore
                .collection(FirestoreCollection.USER.collectionName).document(firebaseAuth.uid!!)
                .collection(FirestoreCollection.CARRIER.collectionName)
                .document(Date().time.toString()) // 현재 시간으로 Carrier 저장
                .set(carrierData)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

}