package com.example.features.placeOrder

import kotlinx.serialization.Serializable

@Serializable
data class PlaceOrderModelRequest(val token: ULong)