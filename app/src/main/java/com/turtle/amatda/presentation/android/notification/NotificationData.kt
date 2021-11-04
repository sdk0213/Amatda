package com.turtle.amatda.presentation.android.notification

data class NotificationData(
    val id: String = "default_notification_id",
    val title: String = "default_notification_title",
    val text: String = "default_notification_text",
    val onGoing: Boolean? = null,
    val isBigText: Boolean = false,
    // isBigText 스타일일때 쇼츠텍스트( =펼침 안내 메시지)
    val shortText: String = "펼쳐서 메모를 확인하세요"
)