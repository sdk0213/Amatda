package com.turtle.amatda.data.api

import com.google.firebase.storage.FirebaseStorage
import com.turtle.amatda.data.model.FirebaseStorageEntity
import io.reactivex.Single
import javax.inject.Inject

class FirebaseStorageApiService @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) {

    fun uploadLocalFile(firebaseStorageEntity: FirebaseStorageEntity): Single<String> {
        return Single.create { emitter ->
            firebaseStorage.reference.child(firebaseStorageEntity.ref.child)
                .child(firebaseStorageEntity.fileName)
                .putFile(firebaseStorageEntity.fileUri)
                .addOnSuccessListener {
                    it.storage.downloadUrl
                        .addOnSuccessListener { uri ->
                            uri?.let {
                                emitter.onSuccess(uri.toString())
                            }
                        }.addOnFailureListener { exception ->
                            emitter.onError(exception)
                        }
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }

        }
    }

}