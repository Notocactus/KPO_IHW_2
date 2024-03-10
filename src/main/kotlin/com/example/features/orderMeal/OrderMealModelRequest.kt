package com.example.features.orderMeal

import kotlinx.serialization.Serializable

@Serializable
data class OrderMealModelRequest(val token: ULong, val mealName: String)
