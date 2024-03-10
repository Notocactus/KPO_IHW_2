package com.example.features.removeMealFromMenu

import kotlinx.serialization.Serializable

@Serializable
data class RemoveMealMenuRequestModel(val token: ULong, val meal: String)
