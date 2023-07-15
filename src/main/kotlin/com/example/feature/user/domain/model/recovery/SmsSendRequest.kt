package com.example.feature.user.domain.model.recovery

data class SmsSendRequest(
    val sender: String = "SMS4B-Test",
    val messages: List<Message>
)

data class Message(
    val number: String = "79674781443",
    val text: String
)
