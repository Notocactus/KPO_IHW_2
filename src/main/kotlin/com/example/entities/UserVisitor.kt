package com.example.entities

class UserVisitor(id: UInt, login: String, password: String): User(id, login, password) {
    val orderBuilder: OrderBuilder = OrderBuilder()
}