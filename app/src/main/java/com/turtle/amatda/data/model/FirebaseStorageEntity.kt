package com.turtle.amatda.data.model

import android.net.Uri

data class FirebaseStorageEntity(
    val ref: AmatdaReference,
    val fileName: String,
    val fileUri: Uri
)

enum class AmatdaReference(val child: String){
    USERS("amatda/users")
}
