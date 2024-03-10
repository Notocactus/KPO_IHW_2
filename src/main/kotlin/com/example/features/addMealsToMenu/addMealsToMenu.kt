package com.example.features.addMealsToMenu

import com.example.entities.UserAdmin
import com.example.entities.AuthenticationManager
import com.example.entities.UserVisitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.addMealsToMenu() {
    routing {
        post("/addMealsToMenu") {
            val result = call.receive<AddMealsToMenuModelRequest>()

            val authManager = AuthenticationManager

            val username = authManager.checkToken(result.token)

            if (username == null) {
                call.respond(HttpStatusCode.Forbidden, "please log in first")
            }

            val user = authManager.getUserByLogin(username!!)

            if (user == null || user is UserVisitor){
                call.respond(HttpStatusCode.Forbidden, "authorise as admin first")
            }
            try{
                (user as UserAdmin).dbAdapter.addMealToMenu(result.itemName, result.itemPrice, result.cookingTime, result.itemAmount)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "smt went wrong")
            }

            call.respond(HttpStatusCode.OK, "added meal menu: " + result.itemName)

        }
    }
}