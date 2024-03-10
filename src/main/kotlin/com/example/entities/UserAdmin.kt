package com.example.entities

import entities.DBAdapter

class UserAdmin(id: UInt, login: String, password: String): User(id, login, password) {
    val dbAdapter: DBAdapter = DBAdapter
}