package com.example.features.removeMealFromOrder

import kotlinx.serialization.Serializable

@Serializable
data class RemoveMealModelRequest(val token: ULong, val meal: String)
