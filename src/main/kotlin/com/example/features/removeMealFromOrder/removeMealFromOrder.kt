package com.example.features.removeMealFromOrder

import com.example.entities.UserAdmin
import com.example.entities.AuthenticationManager
import com.example.entities.UserVisitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.removeMealFromOrder() {
    routing {
        post("/remove_meal_from_order") {
            val result = call.receive<RemoveMealModelRequest>()

            val authManager = AuthenticationManager

            val username = authManager.checkToken(result.token)

            if (username == null) {
                call.respond(HttpStatusCode.Forbidden, "please log in first")
            }

            val user = authManager.getUserByLogin(username!!)

            if (user == null || user is UserAdmin) {
                call.respond(HttpStatusCode.Forbidden, "authorise as visitor first")
            }
            try {
                (user as UserVisitor).orderBuilder.removeMeal(result.meal)
            } catch (e: NoSuchMethodException) {
                call.respond(HttpStatusCode.MethodNotAllowed, e.message.toString())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "can not remove this meal from the order")
            }
            call.respond(HttpStatusCode.OK, "meal removed")
        }
    }
}