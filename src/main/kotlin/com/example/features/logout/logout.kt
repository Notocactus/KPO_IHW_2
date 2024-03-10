package com.example.features.logout

import com.example.entities.AuthenticationManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.security.InvalidParameterException

fun Application.logout() {
    routing {
        post("/logout") {
            val result = call.receive<LogoutModelRequest>()

            val authManager = AuthenticationManager

            try{
                authManager.logOut(result.token)
            }  catch (e: InvalidParameterException) {
                call.respond(HttpStatusCode.Conflict, e.message.toString())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, e.message.toString())
            }
            call.respond(HttpStatusCode.OK, "log out successful")
        }
    }
}
