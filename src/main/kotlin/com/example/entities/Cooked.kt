package com.example.entities

class Cooked(private val order: Order):CookState {
    override fun addMeal(meal: Meal) {
        throw NoSuchMethodException("Order is already cooked, please make a new one.")
    }

    override fun cook() {
        throw NoSuchMethodException("Order is cooked. Nothing more to cook")
    }
}