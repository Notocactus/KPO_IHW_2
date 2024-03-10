package com.example.features.changeMealPrice

import kotlinx.serialization.Serializable

@Serializable
data class ChangeMealPriceModelRequest(val token: ULong, val meal: String, val price: UInt)
