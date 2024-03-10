package com.example

import com.example.features.addMealsToMenu.addMealsToMenu
import com.example.features.cancelOrder.cancelOrder
import com.example.features.changeMealPrice.changeMealPrice
import com.example.features.getIncome.getTotalIncome
import com.example.features.getMenu.getMenu
import com.example.features.getUserActivity.getUserActivity
//import com.example.features.getUserActivity.getUserActivity
import com.example.features.increaseMeal.increaseMeal
import com.example.features.login.login
import com.example.features.logout.logout
import com.example.features.orderMeal.orderMeal
import com.example.features.payForOrder.makePayment
import com.example.features.placeOrder.placeOrder
import com.example.features.registration.register
import com.example.features.removeMealFromMenu.removeMealFromMenu
import com.example.features.removeMealFromOrder.removeMealFromOrder
import com.example.plugins.*
import com.example.util.DatabaseManager
import entities.DBAdapter
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

//    val manager = DatabaseManager()
//    val database = DBAdapter()
//
//    println(manager.performExecute(database.createUsersTableRequest))
//    println(manager.performExecute(database.createMealsTableRequest))
//    println(manager.performExecute(database.createUsersActiveTableRequest))
//    println(manager.performExecute(database.createIncomeTableRequest))

}

fun Application.module() {
    configureSerialization()
    configureRouting()
    addMealsToMenu()
    cancelOrder()
    getTotalIncome()
    getMenu()
    getUserActivity()
    increaseMeal()
    login()
    logout()
    orderMeal()
    makePayment()
    placeOrder()
    register()
    removeMealFromMenu()
    removeMealFromOrder()
    changeMealPrice()
}
