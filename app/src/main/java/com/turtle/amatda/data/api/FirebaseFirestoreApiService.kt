package com.turtle.amatda.data.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import com.turtle.amatda.data.model.*
import com.turtle.amatda.domain.model.ErrorMessage
import com.turtle.amatda.domain.model.Experience
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
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
                emitter.onError(Exception(ErrorMessage.NO_DATA.message))
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
                emitter.onError(Exception(ErrorMessage.NO_USER.message))
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
                .add(carrierData) // 현재 시간으로 Carrier 저장
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    fun importCarrier(): Single<List<CarrierWithPocketAndItemsEntity>> {
        return Single.create { emitter ->
            firebaseAuth.uid?.let {
                fireStore.collection(FirestoreCollection.USER.collectionName)
                    .document(firebaseAuth.uid!!)
                    .collection(FirestoreCollection.CARRIER.collectionName)
                    .orderBy("time_stamp", Query.Direction.DESCENDING)
                    .limit(1)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val result = documentSnapshot.toObjects<CarrierData>()
                        if (!result.isNullOrEmpty()) {
                            emitter.onSuccess(result[0].carrierData)
                        }
                    }
                    .addOnFailureListener {
                        emitter.onError(Exception(ErrorMessage.NO_USER.message))
                    }
            } ?: run {
                emitter.onError(Exception(ErrorMessage.NO_USER.message))
            }
        }
    }

    fun updateUserExp(experience: Experience): Completable {
        return Completable.create { emitter ->
            firebaseAuth.uid?.let { uid ->
                fireStore.collection(FirestoreCollection.USER.collectionName)
                    .document(uid)
                    .update("exp", FieldValue.increment(experience.exp))
                    .addOnSuccessListener {
                        emitter.onComplete()
                    }
                    .addOnFailureListener {
                        emitter.onError(it)
                    }

            } ?: run {
                emitter.onError(Exception(ErrorMessage.NO_USER.message))
            }

        }
    }

}