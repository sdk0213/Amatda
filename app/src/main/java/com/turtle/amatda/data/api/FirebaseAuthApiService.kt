package com.turtle.amatda.data.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.Single
import javax.inject.Inject

class FirebaseAuthApiService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun loginUser(tokenId: String): Single<Boolean> {
        val credential = GoogleAuthProvider.getCredential(tokenId, null)
        return Single.create { emitter ->
            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}