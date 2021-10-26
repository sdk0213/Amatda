package com.turtle.amatda.presentation.android.notification

data class NotificationData(
    val id: String = "default_notification_id",
    val title: String = "default_notification_title",
    val text: String = "default_notification_text",
    val onGoing: Boolean? = null
)