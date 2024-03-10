package com.example.entities

import com.example.storage.ActiveUsers
import entities.DBAdapter
import io.ktor.server.plugins.*
import java.security.InvalidParameterException
import java.text.SimpleDateFormat
import java.util.*

object AuthenticationManager {

    private val dbAdapter: DBAdapter
    private val activeUsers: ActiveUsers

    init {
        dbAdapter = DBAdapter
        activeUsers = ActiveUsers()
    }

    fun addUser(login: String, password: String, admin: Boolean) {
        try {
            dbAdapter.addAccount(login, password, (if (admin) 1 else 2))
        } catch (e: NullPointerException) {
            throw NullPointerException("Something wrong with database")
        } catch (e: ArrayStoreException) {
            throw ArrayStoreException("User with this login already exists")
        } catch (e: Exception) {
            throw Exception("Something went wrong")
        }
    }

    fun loginUser(login: String, password: String): ULong {
        var user: User
        try {
            user = dbAdapter.getUser(login, password)
        } catch (e: NullPointerException) {
            throw NotFoundException("no such user")
        } catch (e: Exception) {
            throw Exception("Something went wrong")
        }



        if (activeUsers.activeUsers.values.indexOf(user.login) == -1) {
            var token: ULong
            do {
                token = (Random().nextInt(1000000000) + 10000000).toULong()
            } while (activeUsers.activeUsers.keys.indexOf(token) != -1)
//            token = (Random().nextInt(1000000000) + 10000000).toULong()
            activeUsers.activeUsers[token] = user.login

            for (item in activeUsers.activeUsers) {
                println(item.key)
                println(item.value)
            }

            return token
        }
        throw InvalidParameterException("user already logged in")
    }

    fun checkToken(token: ULong): String? {
        println(token)
        for (item in activeUsers.activeUsers) {
            println(item.key)
            println(item.value)
        }
        return activeUsers.activeUsers[token]
    }

    fun getUserByLogin(login: String) : User {
        return dbAdapter.getUserByUsername(login)
    }

    fun logOut(token: ULong) {
        val username = activeUsers.activeUsers[token] ?: throw InvalidParameterException("user not logged in")
        val userId = dbAdapter.getUserIdByUsername(username)

        activeUsers.activeUsers.remove(token)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        dbAdapter.addUserActivity(userId, "logout", currentDate)
    }


}