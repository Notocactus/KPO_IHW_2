package com.example.entities

class Order() {
    val meals: MutableList<Meal> = mutableListOf()
    var state: CookState = PreCookingState(this)
    fun getPrice(): UInt{
        var total: UInt = 0u
        for (meal in meals){
            total += meal.price
        }
        return total
    }
    fun addMeal(meal:Meal): Unit{
        state.addMeal(meal)
    }
    fun cook():Unit{
        state.cook()
    }

    fun cancel() {
        if (state !is PreCookingState){
            throw NoSuchMethodException("can't cancel cooking order")
        }
        meals.clear()
    }

    fun removeMeal(meal: Meal) {
        if (state is PreCookingState) meals.remove(meal)
        else throw NoSuchMethodException("can't remove cooking or cooked meals")
    }

}