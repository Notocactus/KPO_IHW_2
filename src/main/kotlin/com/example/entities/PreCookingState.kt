package com.example.entities

class PreCookingState(private val order: Order): CookState {

    override fun addMeal(meal: Meal) {
        order.meals.add(meal)
    }

    override fun cook() {
        order.state = Cooking(order)
        order.state.cook()
    }
}