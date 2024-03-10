package com.example.entities

interface CookState {
    fun addMeal(meal:Meal): Unit
    fun cook():Unit
}