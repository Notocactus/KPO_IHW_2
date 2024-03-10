package com.example.features.cancelOrder

import com.example.entities.UserAdmin
import com.example.entities.AuthenticationManager
import com.example.entities.UserVisitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.cancelOrder() {
    routing {
        post("/cancel_order") {
            val result = call.receive<CancelOrderModelRequest>()

            val authManager = AuthenticationManager

            val username = authManager.checkToken(result.token)

            if (username == null) {
                call.respond(HttpStatusCode.Forbidden, "please log in first")
            }

            val user = authManager.getUserByLogin(username!!)

            if (user == null || user is UserAdmin){
                call.respond(HttpStatusCode.Forbidden, "authorise as visitor first")
            }

            try {
                (user as UserVisitor).orderBuilder.cancelOrder()
            } catch (e: NoSuchMethodException){
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, "smth went wrong")
            }

            call.respond(HttpStatusCode.OK, "order canceled")
        }
    }
}