package com.example.entities

import entities.DBAdapter

class PaymentManager() {
    private val manager = DBAdapter
    private fun validate(cardNumber: UInt): Boolean {
        return cardNumber in 10000000u..100000000u
    }
    fun checkOut(user: User, cardNumber: UInt, order: Order): Boolean {
        if (!validate(cardNumber)) return false
        try {
            manager.addSum(user.id.toInt(), "", order.getPrice())
        } catch (e: NullPointerException) {
            throw NullPointerException("Smth wrong withdatabase")
        } catch (e: Exception) {
            throw Exception("Smth went wrong")
        }
        return true
    }

}