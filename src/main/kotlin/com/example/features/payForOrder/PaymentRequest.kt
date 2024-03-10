package com.example.features.payForOrder

import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(val token: ULong, val cardNumber: UInt)
