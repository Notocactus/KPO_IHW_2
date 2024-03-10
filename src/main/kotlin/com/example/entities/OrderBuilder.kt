package com.example.entities

import entities.DBAdapter

class OrderBuilder {
    private var order: Order = Order()
    private val dbAdapter: DBAdapter = DBAdapter
    fun addMeal(mealName: String){
        val meal: Meal
        try {
            meal = dbAdapter.getMeal(mealName)
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("no such meal")
        }
        dbAdapter.takeMealAway(meal.id)
        order.addMeal(meal)
    }

    fun removeMeal(mealName: String) {
        try{
            order.removeMeal(dbAdapter.getMeal(mealName));
        } catch (e: NullPointerException) {
            throw NoSuchMethodException("can't remove meal")
        } catch (e: IndexOutOfBoundsException) {
            throw e
        } catch (e:NoSuchMethodException) {
            throw e
        } catch (e: Exception) {
            throw Exception("something went wrong")
        }
    }

    fun cookOrder(): Unit { //
        order.cook()
    }

    fun cancelOrder() {
        order.cancel()
        order = Order()
    }

    fun getOrder(): Order? {
        if (order.state is Cooked) return order
        return null
    }


}