package com.example.features.registration

import kotlinx.serialization.Serializable

@Serializable
data class LoginModelRequest (
    val login: String,
    val password: String,
)