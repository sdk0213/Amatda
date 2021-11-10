package com.turtle.amatda.data.model

data class FirestoreEntity(
    val collection: AmatdaCollection,
    val document: String,
    val field: HashMap<String, Any> = hashMapOf()
)

enum class AmatdaCollection(val collectionName: String){
    USER("user")
}
