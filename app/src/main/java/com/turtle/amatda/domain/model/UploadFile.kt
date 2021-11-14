package com.turtle.amatda.domain.model

import android.net.Uri

data class UploadFile(
    val fileName: String,
    val fileUri: Uri
)